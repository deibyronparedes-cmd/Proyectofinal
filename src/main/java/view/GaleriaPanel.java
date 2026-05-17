package view;

import controller.SeñaController;
import model.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;


public class GaleriaPanel extends JPanel {

    private final SeñaController controller;
    private final MainFrame frame;

    private JTextField campoBusqueda;
    private JComboBox<String> comboCategoria;
    private JPanel panelCuadricula;
    private JLabel labelContador;

   
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

        campoBusqueda = new JTextField(20);
        campoBusqueda.setToolTipText("Escribe una letra, número o palabra");
        campoBusqueda.getAccessibleContext()
                     .setAccessibleDescription("Campo de búsqueda de señas");
        campoBusqueda.addActionListener(e -> aplicarFiltro());

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
            panelCuadricula = new JPanel(new GridLayout(0, 4, 10, 10));
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

   
     public void cargarSeñas(List<Seña> señas) {
    panelCuadricula.removeAll();

    if (señas.size() <= 3) {
        panelCuadricula.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        for (Seña seña : señas) {
            JPanel tarjeta = crearTarjetaSeña(seña);
            tarjeta.setPreferredSize(new Dimension(220, 260));
            panelCuadricula.add(tarjeta);
        }
    } else {
        panelCuadricula.setLayout(new GridLayout(0, 4, 10, 10));
        for (Seña seña : señas) {
            panelCuadricula.add(crearTarjetaSeña(seña));
        }
    }

    labelContador.setText("Mostrando " + señas.size() + " seña(s)");
    panelCuadricula.revalidate();
    panelCuadricula.repaint();

    SwingUtilities.invokeLater(() -> {
        panelCuadricula.revalidate();
        panelCuadricula.repaint();
    });
    
    // Forzar repintado
    SwingUtilities.invokeLater(() -> {
        panelCuadricula.revalidate();
        panelCuadricula.repaint();
    });
}

    private JPanel crearTarjetaSeña(Seña seña) {
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            new EmptyBorder(8, 8, 8, 8)
        ));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tarjeta.setToolTipText(seña.getDescripcion());
        tarjeta.setPreferredSize(new Dimension(160, 190));

        JLabel labelImagen = new JLabel();
        labelImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelImagen.setPreferredSize(new Dimension(110, 110));
        labelImagen.setMinimumSize(new Dimension(110, 110));
        labelImagen.setMaximumSize(new Dimension(110, 110));

        if (seña.getImagen() != null) {
            ImageIcon icon = new ImageIcon(
                seña.getImagen().getScaledInstance(110, 110, Image.SCALE_SMOOTH)
            );
            labelImagen.setIcon(icon);
            labelImagen.setIcon(icon);
         labelImagen.setHorizontalAlignment(SwingConstants.CENTER);
         labelImagen.setVerticalAlignment(SwingConstants.CENTER);
         labelImagen.revalidate();
         labelImagen.repaint();
            System.out.println("Mostrando imagen para: " + seña.getNombre());
        } else {
            labelImagen.setText("📷");
            labelImagen.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
            labelImagen.setForeground(Color.LIGHT_GRAY);
            labelImagen.setHorizontalAlignment(SwingConstants.CENTER);
        }

        JLabel labelNombre = new JLabel(seña.getNombre());
        labelNombre.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JLabel labelCat = new JLabel(seña.getCategoria().toString());
        labelCat.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelCat.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        labelCat.setForeground(Color.GRAY);

        tarjeta.add(Box.createVerticalGlue());
        tarjeta.add(labelImagen);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(labelNombre);
        tarjeta.add(labelCat);
        tarjeta.add(Box.createVerticalGlue());

        tarjeta.addMouseListener(new java.awt.event.MouseAdapter() {
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