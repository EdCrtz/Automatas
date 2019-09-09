package Java;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Principal extends JFrame implements ActionListener {
    private JMenuBar barraMenu;
    private JMenu menuArchivo,menuAnalisis;
    // Menu Archivo
    private JMenuItem itemNuevo,itemAbrir,itemGuardar,itemSalir,itemParser,itemScanner,itemArbol;
    private JFileChooser ventanaArchivos;
    private File archivo;
    private JTextPane areaTexto,terminal;
    private JTabbedPane documentos,consola;
    private String [] titulos ={"Tipo","Nombre","Valor"};
    DefaultTableModel modelo = new DefaultTableModel(new Object[0][0],titulos);

    private JTable mitabla = new JTable(modelo);
    private Principal(){
        super("Compilador");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("icono.png").getImage());
        setLayout(new GridLayout(2,0));
        setSize(600,450);
        setLocationRelativeTo(null);
        creaInterFaz();
        setVisible(true);
    }
    private void creaInterFaz() {
        barraMenu = new JMenuBar();
        setJMenuBar(barraMenu);
        menuArchivo = new JMenu("Archivo");
        menuArchivo.setIcon(new ImageIcon("archivo.png"));
        menuAnalisis =  new JMenu("Fases");
        menuAnalisis.setIcon(new ImageIcon("analisis.png"));
        ventanaArchivos = new JFileChooser();
        itemNuevo = new JMenuItem("Nuevo");
        itemAbrir = new JMenuItem("Abrir...");
        itemGuardar = new JMenuItem("Guardar...");
        itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(this);
        itemGuardar.addActionListener(this);
        itemAbrir.addActionListener(this);
        itemNuevo.addActionListener(this);
        itemScanner = new JMenuItem("Scanner");
        itemParser  = new JMenuItem("Parser");
        itemParser.addActionListener(this);
        itemScanner.addActionListener(this);
        ventanaArchivos = new JFileChooser();
        menuArchivo.add(itemNuevo);
        menuArchivo.add(itemAbrir);
        menuArchivo.add(itemGuardar);
        menuArchivo.addSeparator();
        menuArchivo.add(itemSalir);
        menuAnalisis.add(itemScanner);
        menuAnalisis.add(itemParser);
        barraMenu.add(menuArchivo);
        barraMenu.add(menuAnalisis);
        areaTexto = new JTextPane();
        ventanaArchivos= new JFileChooser("Guardar");
        areaTexto.setFont(new Font("Consolas", Font.PLAIN, 12));
        documentos = new JTabbedPane();
        consola = new JTabbedPane();
        documentos.addTab("Nuevo", new JScrollPane(areaTexto));
        areaTexto.setDocument(new Documento());
        documentos.setToolTipText("Aqui se muestra el codigo");
        add(documentos);
        terminal = new JTextPane();
        terminal.setEditable(false);
        consola.addTab("Consola",new JScrollPane(terminal));
        consola.addTab("Tabla",new JScrollPane(mitabla));
        add(consola);
        itemNuevo.setIcon(new ImageIcon("nuevo.png"));
        itemGuardar.setIcon(new ImageIcon("guardar.png"));
        itemAbrir.setIcon(new ImageIcon("abrir.png"));
        itemSalir.setIcon( new ImageIcon("salir.png"));
        itemScanner.setIcon(new ImageIcon("scanner.png"));
        itemParser.setIcon(new ImageIcon("parser.png"));
        documentos.setIconAt(0, new ImageIcon("codigo.png"));
        consola.setIconAt(0, new ImageIcon("consola.png"));
        consola.setIconAt(1, new ImageIcon("tabla.png"));
        consola.setToolTipText("Aqui se muestra el resultado del analisis");
    }

    private void appendToPane(JTextPane tp, String msg, Color c) throws BadLocationException {
        StyledDocument doc = tp.getStyledDocument();
        Style style = tp.addStyle("I'm a Style", null);
        StyleConstants.setForeground(style, c);
        doc.insertString(doc.getLength(),msg,style);

    }
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new Principal();
    }
    public boolean guardar() {
        try {
            if(archivo==null) {
                ventanaArchivos.setDialogTitle("Guardando..");
                ventanaArchivos.setFileSelectionMode(JFileChooser.FILES_ONLY);
                if(ventanaArchivos.showSaveDialog(this)==JFileChooser.CANCEL_OPTION)
                    return false;
                archivo=ventanaArchivos.getSelectedFile();
                documentos.setTitleAt(0, archivo.getName());
            }
            FileWriter fw = new FileWriter(archivo);
            BufferedWriter bf = new BufferedWriter(fw);
            bf.write(areaTexto.getText());
            bf.close();
            fw.close();
        }catch (Exception e) {
            System.out.println("Houston tenemos un problema?");
            return false;
        }
        return true;
    }
    public boolean abrir() {
        String texto="",linea;
        try {
            FileReader fr = new FileReader(archivo);
            BufferedReader br= new BufferedReader(fr);
            while((linea=br.readLine())!=null)
                texto+=linea+"\n";
            areaTexto.setText(texto);
            return true;
        }catch (Exception e) {
            archivo=null;
            JOptionPane.showMessageDialog(null, "Tipo de archivo incompatible", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==itemSalir) {
            System.exit(0);
            return;
        }
        if(e.getSource() == itemAbrir){
            ventanaArchivos.setDialogTitle("Abrir..");
            ventanaArchivos.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if(ventanaArchivos.showOpenDialog(this)==JFileChooser.CANCEL_OPTION)
                return;
            archivo=ventanaArchivos.getSelectedFile();
            documentos.setTitleAt(0, archivo.getName());
            abrir();
        }
        if(e.getSource() == itemGuardar){
            guardar();
            return;
        }
        if(e.getSource()==itemScanner){
            terminal.setText("");
            Scanner analisis = new Scanner(areaTexto.getText());
            analisis.analizar();
            boolean flag = false;
            for (String salida: analisis.dameSalidas()) {
                if(salida.charAt(salida.length()-1)=='$') {
                    flag = true;
                    try {
                        appendToPane(terminal, salida.substring(0, salida.length() - 1) + "\n", Color.RED);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null,salida.substring(0, salida.length() - 1));
                }
                else {
                    try {
                        appendToPane(terminal,salida + "\n",Color.BLACK);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            if(!flag) {
                try {
                    appendToPane(terminal,"El Scanning no tuvo errores \n", Color.GREEN);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
            return;
        }
        if(e.getSource()==itemParser) {
            terminal.setText("");
            Scanner analisis = new Scanner(areaTexto.getText());
            analisis.analizar();
            Parser parser = new Parser(analisis);
            boolean flag = false;
            for (String salida: analisis.dameSalidas()) {
                if(salida.charAt(salida.length()-1)=='$') {
                    flag = true;
                    try {
                        appendToPane(terminal, salida.substring(0, salida.length() - 1) + "\n", Color.RED);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null,salida.substring(0, salida.length() - 1));
                }
                else {
                    try {
                        appendToPane(terminal,salida + "\n",Color.BLACK);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            if(!flag) {
                try {
                    appendToPane(terminal,"El Scanning no tuvo errores \n", Color.GREEN);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
                flag = false;
            }
            parser.program();
            for (String salida:parser.dameSalida()){
                if(salida.charAt(salida.length()-1)=='$') {
                    flag = true;
                    try {
                        appendToPane(terminal, salida.substring(0, salida.length() - 1) + "\n", Color.RED);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    try {
                        appendToPane(terminal,salida + "\n",Color.BLACK);
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            if(!flag) {
                try {
                    appendToPane(terminal,"El Parsing no tuvo errores \n", Color.GREEN);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
