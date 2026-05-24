package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class AccesibilidadConfig extends JDialog {

    private final MainFrame frame;

    private JSlider sliderFuente;
    private JCheckBox checkAltoContraste;
    private JLabel labelPreview;
    private JSpinner spinnerFuente;

    private static int tamañoFuente     = 13;
    private static boolean altoContraste = false;

    
    public AccesibilidadConfig(JFrame frame) {
        super(frame, "Configuración de Accesibilidad", true);
        this.frame = (MainFrame) frame;
        setSize(450, 380);
        setLocationRelativeTo(frame);
        setResizable(false);
        construirUI();
    }

    private void construirUI() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(crearPanelOpciones(), BorderLayout.CENTER);
        panel.add(crearPanelBotones(),  BorderLayout.SOUTH);
        add(panel);
    }

    private JPanel crearPanelOpciones() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 0, 15));

        // ── Tamaño de fuente ────────────────────────────────────────────────
        JPanel panelFuente = new JPanel(new BorderLayout(10, 5));
        panelFuente.setBorder(BorderFactory.createTitledBorder("Tamaño de fuente"));

        sliderFuente = new JSlider(10, 24, tamañoFuente);
        sliderFuente.setMajorTickSpacing(2);
        sliderFuente.setPaintTicks(true);
        sliderFuente.setPaintLabels(true);
        sliderFuente.setToolTipText("Ajusta el tamaño de la fuente en la aplicación");
        sliderFuente.getAccessibleContext()
                    .setAccessibleDescription("Deslizador para ajustar tamaño de fuente");

        spinnerFuente = new JSpinner(new SpinnerNumberModel(tamañoFuente, 10, 24, 1));
        spinnerFuente.setPreferredSize(new Dimension(60, 25));

        sliderFuente.addChangeListener(e -> {
            spinnerFuente.setValue(sliderFuente.getValue());
            actualizarPreview();
        });

        spinnerFuente.addChangeListener(e -> {
            sliderFuente.setValue((int) spinnerFuente.getValue());
            actualizarPreview();
        });

        panelFuente.add(sliderFuente,  BorderLayout.CENTER);
        panelFuente.add(spinnerFuente, BorderLayout.EAST);

        // ── Alto contraste ──────────────────────────────────────────────────
        JPanel panelContraste = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelContraste.setBorder(BorderFactory.createTitledBorder("Contraste"));

        checkAltoContraste = new JCheckBox("Activar alto contraste");
        checkAltoContraste.setSelected(altoContraste);
        checkAltoContraste.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        checkAltoContraste.setToolTipText("Mejora la visibilidad para personas con baja visión");
        checkAltoContraste.getAccessibleContext()
                          .setAccessibleDescription("Activar modo de alto contraste");
        checkAltoContraste.addActionListener(e -> actualizarPreview());

        panelContraste.add(checkAltoContraste);

        // ── Preview ─────────────────────────────────────────────────────────
        JPanel panelPreview = new JPanel(new BorderLayout());
        panelPreview.setBorder(BorderFactory.createTitledBorder("Vista previa"));

        labelPreview = new JLabel("Lengua de Señas Colombiana", SwingConstants.CENTER);
        labelPreview.setFont(new Font("Segoe UI", Font.PLAIN, tamañoFuente));
        labelPreview.setOpaque(true);
        labelPreview.setBackground(Color.WHITE);
        labelPreview.setForeground(Color.BLACK);
        labelPreview.setPreferredSize(new Dimension(0, 50));
        labelPreview.getAccessibleContext()
                    .setAccessibleDescription("Vista previa de la configuración de accesibilidad");

        panelPreview.add(labelPreview, BorderLayout.CENTER);

        panel.add(panelFuente);
        panel.add(panelContraste);
        panel.add(panelPreview);

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        JButton btnAplicar = new JButton("Aplicar");
        btnAplicar.setBackground(new Color(0, 123, 255));
        btnAplicar.setForeground(Color.WHITE);
        btnAplicar.setFocusPainted(false);
        btnAplicar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAplicar.addActionListener(e -> aplicarCambios());

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(e -> dispose());

        JButton btnRestaurar = new JButton("Restaurar defaults");
        btnRestaurar.setFocusPainted(false);
        btnRestaurar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRestaurar.addActionListener(e -> restaurarDefaults());

        panel.add(btnRestaurar);
        panel.add(btnCancelar);
        panel.add(btnAplicar);

        return panel;
    }

    private void actualizarPreview() {
        int tamaño = sliderFuente.getValue();
        labelPreview.setFont(new Font("Segoe UI", Font.PLAIN, tamaño));

        if (checkAltoContraste.isSelected()) {
            labelPreview.setBackground(Color.BLACK);
            labelPreview.setForeground(Color.YELLOW);
        } else {
            labelPreview.setBackground(Color.WHITE);
            labelPreview.setForeground(Color.BLACK);
        }
    }

    private void aplicarCambios() {
        tamañoFuente  = sliderFuente.getValue();
        altoContraste = checkAltoContraste.isSelected();

        aplicarFuenteGlobal(tamañoFuente);

        if (altoContraste) {
            aplicarAltoContraste();
        } else {
            restaurarColores();
        }

        JOptionPane.showMessageDialog(this,
            "Configuración aplicada correctamente.",
            "Accesibilidad",
            JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }

    private void aplicarFuenteGlobal(int tamaño) {
        Font fuente = new Font("Segoe UI", Font.PLAIN, tamaño);
        UIManager.put("Label.font",     fuente);
        UIManager.put("Button.font",    fuente);
        UIManager.put("TextField.font", fuente);
        UIManager.put("TextArea.font",  fuente);
        UIManager.put("Table.font",     fuente);
        UIManager.put("ComboBox.font",  fuente);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void aplicarAltoContraste() {
        UIManager.put("Panel.background",     Color.BLACK);
        UIManager.put("Label.foreground",     Color.YELLOW);
        UIManager.put("Button.background",    Color.DARK_GRAY);
        UIManager.put("Button.foreground",    Color.WHITE);
        UIManager.put("TextField.background", Color.BLACK);
        UIManager.put("TextField.foreground", Color.WHITE);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void restaurarColores() {
        UIManager.put("Panel.background",     new Color(240, 240, 240));
        UIManager.put("Label.foreground",     Color.BLACK);
        UIManager.put("Button.background",    new Color(238, 238, 238));
        UIManager.put("Button.foreground",    Color.BLACK);
        UIManager.put("TextField.background", Color.WHITE);
        UIManager.put("TextField.foreground", Color.BLACK);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void restaurarDefaults() {
        sliderFuente.setValue(13);
        spinnerFuente.setValue(13);
        checkAltoContraste.setSelected(false);
        actualizarPreview();
    }

   
    public static int getTamañoFuente() { return tamañoFuente; }

   
    public static boolean isAltoContraste() { return altoContraste; }
}