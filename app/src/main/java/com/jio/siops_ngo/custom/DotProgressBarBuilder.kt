package com.jio.jioinfra.custom

import android.content.Context
import androidx.annotation.ColorInt


class DotProgressBarBuilder(context: Context) {

    private val dotProgressBar: com.jio.jioinfra.custom.DotProgressBar

    init {
        dotProgressBar = com.jio.jioinfra.custom.DotProgressBar(context)
    }

    fun setDotAmount(amount: Int): DotProgressBarBuilder {
        dotProgressBar.setDotAmount(amount)
        return this
    }

    fun setStartColor(@ColorInt color: Int): DotProgressBarBuilder {
        dotProgressBar.setStartColor(color)
        return this
    }

    fun setEndColor(@ColorInt color: Int): DotProgressBarBuilder {
        dotProgressBar.setEndColor(color)
        return this
    }

    fun setAnimationTime(animationTime: Long): DotProgressBarBuilder {
        dotProgressBar.setAnimationTime(animationTime)
        return this
    }

    fun setAnimationDirection(
            @com.jio.jioinfra.custom.DotProgressBar.AnimationDirection direction: Int): DotProgressBarBuilder {
        dotProgressBar.animationDirection = direction
        return this
    }

    fun build(): DotProgressBar {
        dotProgressBar.reinitialize()
        return dotProgressBar
    }
}