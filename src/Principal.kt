import java.awt.Font
import java.awt.GridLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import javax.swing.*
import javax.swing.table.DefaultTableModel
import javax.swing.ImageIcon
import javax.swing.JScrollPane
import javax.swing.JList
import javax.swing.JTabbedPane
import javax.swing.JFileChooser
import javax.swing.JMenuItem
import javax.swing.JMenu
import javax.swing.JMenuBar
import kotlin.system.exitProcess

class Principal(title: String) : JFrame(title),ActionListener {
    override fun actionPerformed(e: ActionEvent?) {
        if(e!!.source==itemAnalisLexico) {
            val analisis = Scanner(areaTexto.text)
            analisis.analizar()
            val parser = Parser(analisis)
            for (salida in analisis.dameSalidas())
                terminal.text += salida+"\n"
            parser.program()
            for (salida in parser.dameSalida())
                terminal.text += salida+"\n"
		}
		if (e.source==itemSalir) {
			exitProcess(0)
		}
		if(e.source==itemNuevo) {
			documentos.setTitleAt(0, "Nuevo");
            areaTexto.text = ""
			return;
		}
		if(e.source==itemAbrir) {
            ventanaArchivos.dialogTitle = "Abrir..";
            ventanaArchivos.fileSelectionMode = JFileChooser.FILES_ONLY;
			if(ventanaArchivos.showOpenDialog(this)==JFileChooser.CANCEL_OPTION)
				return;
			archivo=ventanaArchivos.getSelectedFile();
			documentos.setTitleAt(0, archivo!!.name);
		}
		if(e.source==itemGuardar) {
		}
    }
    fun guardar(): Boolean {
        try {
            if (archivo == null) {
                ventanaArchivos.dialogTitle = "Guardando.."
                ventanaArchivos.fileSelectionMode = JFileChooser.FILES_ONLY
                if (ventanaArchivos.showSaveDialog(this) == JFileChooser.CANCEL_OPTION)
                    return false
                archivo = ventanaArchivos.selectedFile
                documentos.setTitleAt(0, archivo!!.name)
            }
            val fw = FileWriter(archivo)
            val bf = BufferedWriter(fw)
            bf.write(areaTexto.text)
            bf.close()
            fw.close()
        } catch (e: Exception) {
            println("Houston tenemos un problema?")
            return false
        }

        return true
    }
    private lateinit var barraMenu:JMenuBar
    private lateinit var menuArchivo:JMenu
    private lateinit var menuAnalisis:JMenu
    // Menu Archivo
    private lateinit var itemNuevo:JMenuItem
    private lateinit var itemAbrir:JMenuItem
    private lateinit var itemGuardar:JMenuItem
    private lateinit var itemSalir:JMenuItem
    private lateinit var itemAnalisLexico:JMenuItem
    private lateinit var ventanaArchivos:JFileChooser
    private var archivo: File? = null
    private lateinit var areaTexto:JTextPane
    private lateinit var terminal:JTextPane
    private lateinit var documentos:JTabbedPane
    private lateinit var consola:JTabbedPane
    private val titulos = arrayOf<String>("Tipo", "Nombre", "Valor")
    var modelo = DefaultTableModel(Array<Array<Any>>(0) { arrayOf(arrayOfNulls<Any>(0)) }, titulos)
    private val mitabla = JTable(modelo)
    init {
        setSize(600, 450)
        setLocationRelativeTo(null)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        iconImage = ImageIcon("icono.png").getImage()
		layout = GridLayout(2,0)
        creaInterFaz()
        isVisible = true
    }

    private fun creaInterFaz() {
        barraMenu = JMenuBar()
        jMenuBar = barraMenu
        menuArchivo = JMenu("Archivo")
        menuArchivo.icon = ImageIcon("archivo.png")
        menuAnalisis = JMenu("Analisis")
        menuAnalisis.icon = ImageIcon("analisis.png")
        ventanaArchivos = JFileChooser()
        itemNuevo = JMenuItem("Nuevo")
        itemAbrir = JMenuItem("Abrir...")
        itemGuardar = JMenuItem("Guardar...")
        itemSalir = JMenuItem("Salir")
        itemSalir.addActionListener(this)
        itemGuardar.addActionListener(this)
        itemAbrir.addActionListener(this)
        itemNuevo.addActionListener(this)
        itemAnalisLexico = JMenuItem("Analizar codigo")
        itemAnalisLexico.addActionListener(this)
        ventanaArchivos = JFileChooser()
        menuArchivo.add(itemNuevo)
        menuArchivo.add(itemAbrir)
        menuArchivo.add(itemGuardar)
        menuArchivo.addSeparator()
        menuArchivo.add(itemSalir)
        menuAnalisis.add(itemAnalisLexico)
        barraMenu.add(menuArchivo)
        barraMenu.add(menuAnalisis)
        val docc = Documento()
        areaTexto = JTextPane(docc )
        ventanaArchivos = JFileChooser("Guardar")
        areaTexto.font = Font("Consolas", Font.PLAIN, 12)
        documentos = JTabbedPane()
        consola = JTabbedPane()
        documentos.addTab("Nuevo", JScrollPane(areaTexto))
        documentos.toolTipText = "Aqui se muestra el codigo"
        add(documentos)
        terminal = JTextPane()
        consola.addTab("Consola", JScrollPane(terminal))
        consola.addTab("Tabla", JScrollPane(mitabla))
        add(consola)
        itemNuevo.icon = ImageIcon("nuevo.png")
        itemGuardar.icon = ImageIcon("guardar.png")
        itemAbrir.icon = ImageIcon("abrir.png")
        itemSalir.icon = ImageIcon("salir.png")
        itemAnalisLexico.icon = ImageIcon("lexico.png")
        documentos.setIconAt(0, ImageIcon("codigo.png"))
        consola.setIconAt(0, ImageIcon("consola.png"))
        consola.setIconAt(1, ImageIcon("tabla.png"))
        terminal.isEditable=false
        consola.toolTipText = "Aqui se muestra el resultado del analisis"

    }
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
            Principal("Compilador")
        }
    }
}