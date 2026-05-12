package view;

import controller.SeñaController;
import model.Seña;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Panel que muestra el detalle completo de una seña seleccionada.
 * Incluye imagen ampliada, descripción y categoría.
 */
public class DetalleSeñaPanel extends JPanel {

    private final SeñaController controller;
    private final MainFrame frame;

    private JLabel labelImagen;
    private JLabel labelNombre;
    private JLabel labelCategoria;
    private JTextArea areaDescripcion;
    private JLabel labelRepresentacion;

    /**
     * Constructor principal.
     * @param controller controlador de la aplicación
     * @param frame ventana principal
     */
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
        panel.add(labelNombre,