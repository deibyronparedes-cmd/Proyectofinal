package view;

import controller.SeñaController;
import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame {

    private final SeñaController controller;
    private final CardLayout cardLayout;
    private final JPanel panelContenido;

    public static final String PANEL_GALERIA   = "GALERIA";
    public static final String PANEL_TRADUCTOR = "TRADUCTOR";
    public static final String PANEL_HISTORIAL = "HISTORIAL";
    public static final String PANEL_DETALLE   = "DETALLE";

 
    public MainFrame() {
        this.controller     = new SeñaController();
        this.cardLayout     = new CardLayout();
        this.panelContenido = new JPanel(cardLayout);

        configurarVentana();
        construirUI();
        mostrarPanel(PANEL_GALERIA);
    }

    private void configurarVentana() {
        setTitle("Sistema de Lengua de Señas Colombiana");
        setSize(1000, 700);
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void construirUI() {
        add(crearBarraNavegacion(), BorderLayout.NORTH);

        panelContenido.add(new GaleriaPanel(controller, this),    PANEL_GALERIA);
        panelContenido.add(new TraductorPanel(controller, this),   PANEL_TRADUCTOR);
        panelContenido.add(new HistorialPanel(controller, this),   PANEL_HISTORIAL);
        panelContenido.add(new DetalleSeñaPanel(controller, this), PANEL_DETALLE);

        add(panelContenido, BorderLayout.CENTER);
        add(crearBarraEstado(), BorderLayout.SOUTH);
    }

    private JPanel crearBarraNavegacion() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        barra.setBackground(new Color(33, 37, 41));

        JLabel titulo = new JLabel("🤟 Señas Colombia");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        barra.add(titulo);

        barra.add(Box.createHorizontalStrut(30));

        barra.add(crearBotonNav("Galería",   PANEL_GALERIA));
        barra.add(crearBotonNav("Traductor", PANEL_TRADUCTOR));
        barra.add(crearBotonNav("Historial", PANEL_HISTORIAL));

        // Botón de accesibilidad
        JButton btnAccesibilidad = new JButton("⚙ Accesibilidad");
        btnAccesibilidad.setForeground(Color.WHITE);
        btnAccesibilidad.setBackground(new Color(52, 58, 64));
        btnAccesibilidad.setBorderPainted(false);
        btnAccesibilidad.setFocusPainted(false);
        btnAccesibilidad.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnAccesibilidad.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAccesibilidad.setToolTipText("Configurar accesibilidad");
        btnAccesibilidad.getAccessibleContext()
                        .setAccessibleDescription("Abrir configuración de accesibilidad");

        JFrame ventanaPadre = this;
        btnAccesibilidad.addActionListener(e ->
                new AccesibilidadConfig(ventanaPadre).setVisible(true));

        barra.add(btnAccesibilidad);

        return barra;
    }

    private JButton crearBotonNav(String texto, String panel) {
        JButton btn = new JButton(texto);
        btn.setForeground(Color.YELLOW);
        btn.setBackground(new Color(52, 58, 64));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setToolTipText("Ir a " + texto);
        btn.getAccessibleContext()
           .setAccessibleDescription("Navegar a " + texto);
        btn.addActionListener(e -> mostrarPanel(panel));
        return btn;
    }

    private JPanel crearBarraEstado() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT));
        barra.setBackground(new Color(248, 249, 250));
        barra.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
        JLabel estado = new JLabel("Total de señas disponibles: "
                + controller.totalSeñas());
        estado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        estado.setForeground(Color.GRAY);
        barra.add(estado);
        return barra;
    }

    
    public void mostrarPanel(String nombre) {
        cardLayout.show(panelContenido, nombre);
    }

    
    public SeñaController getController() {
        return controller;
    }

   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("No se pudo aplicar LookAndFeel: "
                        + e.getMessage());
            }
            new MainFrame().setVisible(true);
        });
    }
}