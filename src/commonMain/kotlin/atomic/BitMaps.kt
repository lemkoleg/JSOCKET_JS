package atomic

import com.soywiz.korim.bitmap.Bitmap32
import com.soywiz.korim.color.RGBAPremultiplied
import com.soywiz.korim.color.RgbaPremultipliedArray


fun Bitmap32.mipmapWithKoef(koef: Float): Bitmap32 {
    val temp = this.clone()
    temp.premultiplyInplaceIfRequired()
    val dst = RgbaPremultipliedArray(temp.ints)

    var twidth = width
    var theight = height
    val kk: Float = 1 - koef
    var y_koef = 0.0f

    twidth = (koef * twidth).toInt()
    theight *= (koef * theight).toInt()
    for (y in 0 until (theight - 1)) {
        y_koef += kk
        var n = temp.index(0, y)
        var m = temp.index(0, ((y + y_koef).toInt()))

        for (x in 0 until (twidth - 1)) {
            val c1 = dst[m + 0]
            val c2 = dst[m + 1]
            val c3 = dst[m + width + 0]
            val c4 = dst[m + width + 1]
            dst[n] = RGBAPremultiplied.blend(c1, c2, c3, c4)
            m += 2
            n++
        }
    }
    return temp.copySliceWithSize(0, 0, (twidth - 1), (theight - 1))
}