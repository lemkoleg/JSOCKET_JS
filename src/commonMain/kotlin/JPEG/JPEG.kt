package JPEG

import com.soywiz.korim.format.*
import com.soywiz.korio.stream.SyncStream
import com.soywiz.korio.stream.readAll
import com.soywiz.korio.stream.writeBytes

object JPEG : ImageFormat("jpg", "jpeg") {
    override fun decodeHeader(s: SyncStream, props: ImageDecodingProps): ImageInfo? = try {
        val info = JPEGDecoder.decodeInfo(s.readAll())
        ImageInfo().apply {
            this.width = info.width
            this.height = info.height
            this.bitsPerPixel = 24
        }
    } catch (e: Throwable) {
        null
    }

    override fun readImage(s: SyncStream, props: ImageDecodingProps): ImageData {
        @Suppress("DEPRECATION")
        return ImageData(listOf(ImageFrame(JPEGDecoder.decode(s.readAll()))))
    }

    override fun writeImage(image: ImageData, s: SyncStream, props: ImageEncodingProps) {
        s.writeBytes(JPEGEncoder.encode(image.mainBitmap.toBMP32(), quality = (props.quality * 100).toInt()))
    }
}