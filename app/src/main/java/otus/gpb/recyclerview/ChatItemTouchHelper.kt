package otus.gpb.recyclerview

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.VectorDrawable
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.UUID
import kotlin.math.roundToInt


class ChatItemTouchHelper(private val listener: ItemListener) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.ACTION_STATE_IDLE,
    ItemTouchHelper.LEFT
) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(
        viewHolder: RecyclerView.ViewHolder,
        direction: Int
    ) {
        viewHolder.getElementId()?.let { listener.onSwipe(it) }
    }

    /**
     * source: https://stackoverflow.com/questions/30820806/adding-a-colored-background-with-text-icon-under-swiped-row-when-using-androids
     * */
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        // Get RecyclerView item from the ViewHolder
        val itemView = viewHolder.itemView

        val p = Paint()
        val context = itemView.context
        val resources: Resources = context.resources

        if (dX < 0) {
            /* Set your color for negative displacement */
            p.setColor(ContextCompat.getColor(context, R.color.light_blue))
            // Draw Rect with varying left side, equal to the item's right side plus negative displacement dX

            val itemViewTop = itemView.top.toFloat()
            val itemViewRight = itemView.right.toFloat()
            val itemViewBottom = itemView.bottom.toFloat()
            c.drawRect(
                /* left = */ itemViewRight + dX,
                /* top = */ itemViewTop,
                /* right = */ itemViewRight,
                /* bottom = */ itemViewBottom,
                /* paint = */ p
            )

//            fixme magic numbers to dimensions
            getVectorBitmap(context, R.drawable.archive)?.let { bitmap ->
                val left = itemViewRight - convertDpToPx(24, resources) - bitmap.getWidth()
                val top = itemViewTop + (itemViewBottom - itemViewTop - bitmap.getHeight()) * 0.4f
                c.drawBitmap(
                    /* bitmap = */ bitmap,
                    /* left = */ left,
                    /* top = */ top,
                    /* paint = */ p
                )

                c.drawText(
                    "Archive",
                    left - convertDpToPx(15, resources),
                    top + convertDpToPx(40, resources),
                    Paint().apply {
                        color = ContextCompat.getColor(context, R.color.white)
                        textSize = 36f
                    }
                )
            }
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    /**
     * source https://stackoverflow.com/questions/44612353/using-vector-drawable-by-drawing-in-canvas
     * */
    private fun getVectorBitmap(context: Context, drawableId: Int) =
        ContextCompat.getDrawable(context, drawableId)?.asClass<VectorDrawable>()
            ?.let { drawable ->
                val bitmap: Bitmap = createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight)
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
                bitmap
            }

    private fun convertDpToPx(dp: Int, resources: Resources): Int {
        return (dp * (resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
    }

    private fun RecyclerView.ViewHolder.getElementId(): UUID? {
        return bindingAdapter
            ?.asClass<ChatDiffAdapter>()
            ?.currentList
            ?.getOrNull(bindingAdapterPosition)?.id
    }
}