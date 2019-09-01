import java.awt.Color
import java.util.ArrayList
import javax.swing.text.AttributeSet
import javax.swing.text.BadLocationException
import javax.swing.text.DefaultStyledDocument
import javax.swing.text.MutableAttributeSet
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.StyleConstants
import javax.swing.text.StyleContext
class Documento:DefaultStyledDocument() {
    private val cont:StyleContext
    private val attr:AttributeSet
    private val attrBlack:AttributeSet
    //r_CAD = new Color(42, 0, 255);
    private lateinit var a:ArrayList<coloreado>
    internal lateinit var currentString:String
    internal lateinit var oldString:String
    init{
        cont = StyleContext.getDefaultStyleContext()
        attr = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, r_PR)
        attrBlack = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK)
        //attrBlue = cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, r_CAD);
    }
    @Throws(BadLocationException::class)
    public override fun insertString(arg0:Int, arg1:String, arg2:AttributeSet) {
        super.insertString(arg0, arg1, arg2)
        algo()
    }
    @Throws(BadLocationException::class)
    public override fun remove(arg0:Int, arg1:Int) {
        // TODO Auto-generated method stub
        super.remove(arg0, arg1)
        algo()
    }
    @Synchronized @Throws(BadLocationException::class)
    private fun algo() {
        var asnew: MutableAttributeSet? = null
        a = ArrayList<coloreado>()
        colorea()
        asnew = SimpleAttributeSet(attrBlack.copyAttributes())
        StyleConstants.setBold(asnew, false)
        setCharacterAttributes(0, getText(0, getLength()).length, asnew, true)
        for (i in a.indices)
        {
            asnew = SimpleAttributeSet(attr.copyAttributes())
            StyleConstants.setBold(asnew, true)
            setCharacterAttributes(a.get(i).pos, a.get(i).palabra.length, asnew, true)
        }
    }
    @Throws(BadLocationException::class)
    private fun colorea() {
        var t = getText(0, getLength())
        var P = ""
        currentString = t
        t += " "
        var delimitador = 0
        for (i in 0 until t.length)
        {
            val car = t.get(i)
            if (Character.isLetterOrDigit(car) || car == '_')
            {
                P += car
            }
            else
            {
                delimitador = i
                if (P.length > 0)
                {
                    if (palabraRes(P))
                    {
                        a.add(coloreado(delimitador - P.length, P))
                    }
                    P = ""
                }
            }
        }
        oldString = currentString
    }
    private fun palabraRes(palabra:String):Boolean {
        if (palabra.matches(("(do|until|true|false|class|boolean|int|system|ln|readln|float)").toRegex()))
            return true
        return false
    }
    internal inner class coloreado(po:Int, pa:String) {
        var pos:Int = 0
        var palabra:String
        init{
            pos = po
            palabra = pa
        }
    }
    companion object {
        private const val serialVersionUID = 1L
        private val r_PR = Color.decode("#7F0091")
    }
}