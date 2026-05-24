package view;

import controller.SeñaController;
import model.Seña;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class DetalleSeñaPanel extends JPanel {

    private final SeñaController controller;
    private final MainFrame frame;

    private JLabel labelImagen;
    private JLabel labelNombre;
    private JLabel labelCategoria;
    private JTextArea areaDescripcion;
    private JLabel labelRepresentacion;

  
    public DetalleSeñaPanel(SeñaController controller, MainFrame frame) {
        this.controller = controller;
        this.frame      = frame;
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        construirUI();
    }

    private void construirUI() {
        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelCentral(), BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnVolver = new JButton("← Volver a Galería");
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnVolver.addActionListener(e -> frame.mostrarPanel(MainFrame.PANEL_GALERIA));
        btnVolver.getAccessibleContext()
                 .setAccessibleDescription("Regresar al panel de galería de señas");

        panel.add(btnVolver);
        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Imagen grande
        labelImagen = new JLabel("", SwingConstants.CENTER);
        labelImagen.setPreferredSize(new Dimension(250, 250));
        labelImagen.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            new EmptyBorder(10, 10, 10, 10)
        ));
        labelImagen.setBackground(new Color(248, 249, 250));
        labelImagen.setOpaque(true);
        labelImagen.getAccessibleContext()
                   .setAccessibleDescription("Imagen de la seña seleccionada");

        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridheight = 3;
        panel.add(labelImagen, gbc);

        // Nombre
        labelNombre = new JLabel("Selecciona una seña");
        labelNombre.setFont(new Font("Segoe UI", Font.BOLD, 28));
        labelNombre.setForeground(new Color(33, 37, 41));

        gbc.gridx = 1; gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(labelNombre, gbc);

        // Categoría
        labelCategoria = new JLabel("");
        labelCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelCategoria.setForeground(new Color(108, 117, 125));

        gbc.gridy = 1;
        panel.add(labelCategoria, gbc);

        // Representación
        labelRepresentacion = new JLabel("");
        labelRepresentacion.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        labelRepresentacion.setForeground(new Color(0, 123, 255));

        gbc.gridy = 2;
        panel.add(labelRepresentacion, gbc);

        return panel;
    }

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Descripción"));

        areaDescripcion = new JTextArea(4, 50);
        areaDescripcion.setEditable(false);
        areaDescripcion.setLineWrap(true);
        areaDescripcion.setWrapStyleWord(true);
        areaDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        areaDescripcion.setBackground(new Color(248, 249, 250));
        areaDescripcion.setBorder(new EmptyBorder(8, 8, 8, 8));
        areaDescripcion.getAccessibleContext()
                       .setAccessibleDescription("Descripción detallada de la seña");

        panel.add(new JScrollPane(areaDescripcion), BorderLayout.CENTER);
        return panel;
    }

   
    public void mostrarSeña(Seña seña) {
        if (seña == null) {
            JOptionPane.showMessageDialog(this,
                "No se pudo cargar el detalle de la seña.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        labelNombre.setText(seña.getNombre());
        labelCategoria.setText("Categoría: " + seña.getCategoria());
        labelRepresentacion.setText("Representación: " + seña.getTextoRepresentacion());
        areaDescripcion.setText(seña.getDescripcion());

        if (seña.getImagen() != null) {
            Image img = seña.getImagen().getScaledInstance(230, 230, Image.SCALE_SMOOTH);
            labelImagen.setIcon(new ImageIcon(img));
            labelImagen.setText("");
        } else {
            labelImagen.setIcon(null);
            labelImagen.setText("Sin imagen");
            labelImagen.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            labelImagen.setForeground(Color.GRAY);
        }

        labelImagen.getAccessibleContext()
                   .setAccessibleDescription("Imagen de la seña: " + seña.getNombre());
    }
}