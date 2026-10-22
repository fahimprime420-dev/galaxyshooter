package com.example.galaxyshooter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.galaxyshooter.entities.*
import kotlin.random.Random

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback, Runnable {

    private var running = true
    private var thread: Thread? = null

    private val player = Player()
    private val enemies = mutableListOf<Enemy>()
    private val bullets = mutableListOf<Bullet>()
    private val particles = mutableListOf<Particle>()
    private var stars = List(120) {
        Star(Random.nextFloat(), Random.nextFloat(),
             Random.nextFloat() * 2f + 0.4f, Random.nextFloat() * 1.8f + 0.6f)
    }

    private var score = 0
    private var spawnTimer = 0
    private var tick = 0L

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init { holder.addCallback(this) }

    override fun surfaceCreated(h: SurfaceHolder) {
        running = true
        thread = Thread(this).also { it.start() }
    }
    override fun surfaceChanged(h: SurfaceHolder, f: Int, w: Int, ht: Int) {}
    override fun surfaceDestroyed(h: SurfaceHolder) {
        running = false
        thread?.join()
    }

    override fun run() {
        var last = System.nanoTime()
        while (running) {
            val now = System.nanoTime()
            val dt = (now - last) / 1_000_000_000.0
            last = now
            if (dt > 0.05) continue
            update(dt)
            val c = holder.lockCanvas() ?: continue
            try { draw(c) } finally { holder.unlockCanvasAndPost(c) }
        }
    }

    private fun update(dt: Double) {
        tick++
        stars = stars.map {
            val ny = (it.y + it.speed * dt * 60).toFloat()
            if (ny > 1f) it.copy(x = Random.nextFloat(), y = 0f) else it.copy(y = ny)
        }
        if (tick % 8L == 0L) bullets.add(Bullet(player.x, player.y - 22, 0f, -12f, false))
        bullets.forEach { it.x += it.vx; it.y += it.vy }
        bullets.removeAll { it.y < -20 || it.y > height + 20 }

        if (++spawnTimer > 55) {
            spawnTimer = 0
            val ex = Random.nextFloat() * (width - 100) + 50
            enemies.add(Enemy(ex, -40f, Random.nextFloat() * 1.2f + 1.6f))
        }
        enemies.forEach { it.y += it.vy }

        val it = bullets.iterator()
        while (it.hasNext()) {
            val b = it.next()
            for (e in enemies) {
                if (Math.hypot((b.x - e.x).toDouble(), (b.y - e.y).toDouble()) < e.r + 6) {
                    it.remove(); e.hp--
                    for (i in 0..5) particles.add(Particle(e.x, e.y))
                    if (e.hp <= 0) { score += 10; enemies.remove(e) }
                    break
                }
            }
        }
        enemies.removeAll { it.y > height + 80 }
        particles.forEach { it.x += it.vx; it.y += it.vy; it.life-- }
        particles.removeAll { it.life <= 0 }
    }

    private fun draw(c: Canvas) {
        c.drawColor(Color.rgb(8, 6, 26))
        paint.color = Color.WHITE
        stars.forEach {
            paint.alpha = (80 + (it.size * 60)).toInt().coerceIn(0, 255)
            c.drawCircle(it.x * width, it.y * height, it.size, paint)
        }
        paint.alpha = 255

        paint.color = Color.rgb(120, 230, 255)
        c.drawCircle(player.x, player.y, 22f, paint)
        paint.color = Color.WHITE
        c.drawCircle(player.x, player.y - 6, 8f, paint)

        paint.color = Color.rgb(140, 240, 255)
        bullets.forEach { c.drawCircle(it.x, it.y, 5f, paint) }

        paint.color = Color.rgb(235, 90, 130)
        enemies.forEach { c.drawCircle(it.x, it.y, it.r, paint) }

        particles.forEach {
            paint.color = Color.argb(it.life * 10, 255, 200, 120)
            c.drawCircle(it.x, it.y, 3f, paint)
        }

        paint.color = Color.WHITE
        paint.textSize = 48f
        c.drawText("SCORE  $score", 40f, 90f, paint)
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        player.x = e.x
        player.y = e.y.coerceAtLeast(120f)
        return true
    }
}
