package com.kido1611.dicoding.moviecatalogue.fragment.tvs

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TvsViewModelTest {
    private lateinit var viewModel: TvsViewModel

    @Before
    fun setup() {
        viewModel = TvsViewModel()
    }

    @Test
    fun testGetTvs() {
        val tvs = viewModel.list()
        Assert.assertEquals(10, tvs.size)
    }

    @Test
    fun testIsRealTvOnGetTvs() {
        val tv = viewModel.list()[0]
        Assert.assertEquals(false, tv.isMovie())
    }
}