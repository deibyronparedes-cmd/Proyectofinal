package view;

import controller.SeñaController;
import model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * Panel que muestra la galería de señas en una cuadrícula.
 * Permite filtrar por categoría y buscar por nombre.
 */
public class GaleriaPanel extends JPanel {

    private final SeñaController controller;
    private final MainFrame frame;

    private JTextField campoBusqueda;
    private JComboBox<String> comboCategoria;
    private JPanel panelCuadricula;
    private JLabel labelContador;

    /**
     * Constructor principal.
     * @param controller controlador de la aplicación
     * @param frame ventana principal
     */
    public GaleriaPanel(SeñaController controller, MainFrame frame) {
        this.controller = controller;
        this.frame      = frame;
        setLayout(new BorderLayout(0, 10));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        construirUI();
        cargarSeñas(controller.obtenerTodas());
    }

    private void construirUI() {
        add(crearPanelFiltros(), BorderLayout.NORTH);
        add(crearPanelGaleria(), BorderLayout.CENTER);
    }

    private JPanel crearPanelFiltros() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Buscar y filtrar"));

        // Campo de búsqueda
        campoBusqueda = new JTextField(20);
        campoBusqueda.setToolTipText("Escribe una letra, número o palabra");
        campoBusqueda.getAccessibleContext()
                     .setAccessibleDescription("Campo de búsqueda de señas");
        campoBusqueda.addActionListener(e -> aplicarFiltro());

        // Combo de categorías
        comboCategoria = new JComboBox<>(new String[]{
            "Todas", "LETRA", "NUMERO", "PALABRA", "FRASE"
        });
        comboCategoria.setToolTipText("Filtrar por categoría");
        comboCategoria.addActionListener(e -> aplicarFiltro());

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(0, 123, 255));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(e -> aplicarFiltro());

        JButton btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLimpiar.addActionListener(e -> limpiarFiltros());

        labelContador = new JLabel();
        labelContador.setForeground(Color.GRAY);
        labelContador.setFont(new Font("Segoe UI", Font.ITALIC, 11));

        panel.add(new JLabel("Buscar:"));
        panel.add(campoBusqueda);
        panel.add(new JLabel("Categoría:"));
        panel.add(comboCategoria);
        panel.add(btnBuscar);
        panel.add(btnLimpiar);
        panel.add(labelContador);

        return panel;
    }

    private JScrollPane crearPanelGaleria() {
        panelCuadricula = new JPanel(new GridLayout(0, 5, 10, 10));
        panelCuadricula.setBorder(new EmptyBorder(10, 5, 10, 5));

        JScrollPane scroll = new JScrollPane(panelCuadricula);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        return scroll;
    }

    private void aplicarFiltro() {
        String texto     = campoBusqueda.getText().trim();
        String categoria = (String) comboCategoria.getSelectedItem();

        List<Seña> resultado;

        if (!"Todas".equals(categoria)) {
            CategoriaSeña cat = CategoriaSeña.valueOf(categoria);
            resultado = controller.buscar(texto, cat);
        } else {
            resultado = controller.buscar(texto);
        }

        // Validación con mensaje al usuario
        if (resultado.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No se encontraron señas para: \"" + texto + "\"",
                "Sin resultados",
                JOptionPane.INFORMATION_MESSAGE);
        }

        cargarSeñas(resultado);
    }

    private void limpiarFiltros() {
        campoBusqueda.setText("");
        comboCategoria.setSelectedIndex(0);
        cargarSeñas(controller.obtenerTodas());
    }

    /**
     * Carga y muestra las señas en la cuadrícula.
     * @param señas lista de señas a mostrar
     */
    public void cargarSeñas(List<Seña> señas) {
        panelCuadricula.removeAll();

        for (Seña seña : señas) {
            panelCuadricula.add(crearTarjetaSeña(seña));
        }

        labelContador.setText("Mostrando " + señas.size() + " seña(s)");
        panelCuadricula.revalidate();
        panelCuadricula.repaint();
    }

    private JPanel crearTarjetaSeña(Seña seña) {
        JPanel tarjeta = new JPanel(new BorderLayout(5, 5));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            new EmptyBorder(8, 8, 8, 8)
        ));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tarjeta.setToolTipText(seña.getDescripcion());

        // Imagen o placeholder
        JLabel labelImagen = new JLabel("", SwingConstants.CENTER);
        labelImagen.setPreferredSize(new Dimension(100, 100));
        if (seña.getImagen() != null) {
            Image img = seña.getImagen().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            labelImagen.setIcon(new ImageIcon(img));
        } else {
            labelImagen.setText("📷");
            labelImagen.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
            labelImagen.setForeground(Color.LIGHT_GRAY);
        }

        // Nombre
        JLabel labelNombre = new JLabel(seña.getNombre(), SwingConstants.CENTER);
        labelNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));

        // Categoría
        JLabel labelCat = new JLabel(seña.getCategoria().toString(), SwingConstants.CENTER);
        labelCat.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        labelCat.setForeground(Color.GRAY);

        JPanel panelTexto = new JPanel(new GridLayout(2, 1));
        panelTexto.setOpaque(false);
        panelTexto.add(labelNombre);
        panelTexto.add(labelCat);

        tarjeta.add(labelImagen, BorderLayout.CENTER);
        tarjeta.add(panelTexto, BorderLayout.SOUTH);

        // Clic → ir al detalle
        tarjeta.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                DetalleSeñaPanel detalle = (DetalleSeñaPanel)
                    frame.getContentPane()
                         .getComponent(1); // panelContenido
                // Navegar y mostrar detalle
                frame.mostrarPanel(MainFrame.PANEL_DETALLE);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                tarjeta.setBackground(new Color(240, 248, 255));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                tarjeta.setBackground(Color.WHITE);
            }
        });

        return tarjeta;
    }
}