package view;

import controller.SeñaController;
import model.Seña;
import repository.HistorialDAO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;


public class TraductorPanel extends JPanel {

    private final SeñaController controller;
    private final MainFrame frame;
    private final HistorialDAO historialDAO;

    private JTextArea campoTexto;
    private JPanel panelResultado;
    private JLabel labelEstado;
    private JSpinner spinnerLimite;

    
    public TraductorPanel(SeñaController controller, MainFrame frame) {
        this.controller  = controller;
        this.frame       = frame;
        this.historialDAO = new HistorialDAO();
        setLayout(new BorderLayout(0, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        construirUI();
    }

    private void construirUI() {
        add(crearPanelEntrada(), BorderLayout.NORTH);
        add(crearPanelResultado(), BorderLayout.CENTER);
        add(crearPanelEstado(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelEntrada() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Texto a traducir"));

        // Área de texto
        campoTexto = new JTextArea(4, 40);
        campoTexto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoTexto.setLineWrap(true);
        campoTexto.setWrapStyleWord(true);
        campoTexto.setToolTipText("Escribe el texto que deseas traducir a señas");
        campoTexto.getAccessibleContext()
                  .setAccessibleDescription("Campo de texto para traducción a señas");

        JScrollPane scrollTexto = new JScrollPane(campoTexto);

        // Panel de controles
        JPanel panelControles = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JLabel labelLimite = new JLabel("Límite de señas:");
        spinnerLimite = new JSpinner(new SpinnerNumberModel(50, 1, 200, 1));
        spinnerLimite.setToolTipText("Máximo número de señas a mostrar");
        spinnerLimite.setPreferredSize(new Dimension(70, 25));

        JButton btnTraducir = new JButton("Traducir");
        btnTraducir.setBackground(new Color(40, 167, 69));
        btnTraducir.setForeground(Color.WHITE);
        btnTraducir.setFocusPainted(false);
        btnTraducir.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnTraducir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTraducir.setToolTipText("Traducir el texto ingresado a lengua de señas");
        btnTraducir.addActionListener(e -> traducir());

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLimpiar.addActionListener(e -> limpiar());

        panelControles.add(labelLimite);
        panelControles.add(spinnerLimite);
        panelControles.add(btnTraducir);
        panelControles.add(btnLimpiar);

        panel.add(scrollTexto, BorderLayout.CENTER);
        panel.add(panelControles, BorderLayout.SOUTH);

        return panel;
    }

    private JScrollPane crearPanelResultado() {
        panelResultado = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelResultado.setBorder(BorderFactory.createTitledBorder("Secuencia de señas"));
        panelResultado.setBackground(new Color(248, 249, 250));

        JScrollPane scroll = new JScrollPane(panelResultado);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    private JPanel crearPanelEstado() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelEstado = new JLabel("Ingresa un texto y presiona Traducir");
        labelEstado.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        labelEstado.setForeground(Color.GRAY);
        panel.add(labelEstado);
        return panel;
    }

    private void traducir() {
        String texto = campoTexto.getText().trim();

        // ── Validaciones ────────────────────────────────────────────────────
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor ingresa un texto para traducir.",
                "Campo vacío",
                JOptionPane.WARNING_MESSAGE);
            campoTexto.requestFocus();
            return;
        }

        if (texto.length() > 500) {
            JOptionPane.showMessageDialog(this,
                "El texto no puede superar los 500 caracteres.\n"
                + "Actualmente tiene: " + texto.length() + " caracteres.",
                "Texto demasiado largo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!texto.matches(".*[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9].*")) {
            JOptionPane.showMessageDialog(this,
                "El texto debe contener al menos una letra o número.",
                "Texto inválido",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ── Traducción ──────────────────────────────────────────────────────
        try {
            int limite = (int) spinnerLimite.getValue();
            List<Seña> señas = controller.traducir(texto, limite);

            if (señas.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "No se encontraron señas para el texto ingresado.\n"
                    + "Intenta con letras individuales (A, B, C...).",
                    "Sin resultados",
                    JOptionPane.INFORMATION_MESSAGE);
                labelEstado.setText("Sin resultados para: \"" + texto + "\"");
                return;
            }

            // Guardar en MySQL
            historialDAO.guardar(
                controller.obtenerHistorial()
                          .get(controller.obtenerHistorial().size() - 1)
            );

            mostrarResultado(señas, texto);

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                "Error al traducir: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarResultado(List<Seña> señas, String textoOriginal) {
        panelResultado.removeAll();

        for (Seña seña : señas) {
            panelResultado.add(crearTarjetaMini(seña));
        }

        labelEstado.setText("Traducción de \"" + textoOriginal
            + "\" → " + señas.size() + " seña(s) encontradas");

        panelResultado.revalidate();
        panelResultado.repaint();
    }

    private JPanel crearTarjetaMini(Seña seña) {
        JPanel tarjeta = new JPanel(new BorderLayout(3, 3));
        tarjeta.setPreferredSize(new Dimension(90, 110));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            new EmptyBorder(4, 4, 4, 4)
        ));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setToolTipText(seña.getDescripcion());

        JLabel labelImagen = new JLabel("", SwingConstants.CENTER);
        if (seña.getImagen() != null) {
            Image img = seña.getImagen().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            labelImagen.setIcon(new ImageIcon(img));
        } else {
            labelImagen.setText("🤟");
            labelImagen.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        }

        JLabel labelNombre = new JLabel(seña.getNombre(), SwingConstants.CENTER);
        labelNombre.setFont(new Font("Segoe UI", Font.BOLD, 12));

        tarjeta.add(labelImagen, BorderLayout.CENTER);
        tarjeta.add(labelNombre, BorderLayout.SOUTH);

        return tarjeta;
    }

    private void limpiar() {
        campoTexto.setText("");
        panelResultado.removeAll();
        panelResultado.revalidate();
        panelResultado.repaint();
        labelEstado.setText("Ingresa un texto y presiona Traducir");
        campoTexto.requestFocus();
    }
}