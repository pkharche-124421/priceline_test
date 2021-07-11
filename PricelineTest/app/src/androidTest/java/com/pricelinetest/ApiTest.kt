package com.pricelinetest

import com.pricelinetest.network.ApiService
import com.pricelinetest.models.Name
import com.pricelinetest.network.apimodels.response.ResponseName
import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ApiTest {
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Mock
    var serverApi: ApiService? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun fetchNamesData() {
        GlobalScope.launch {
            Mockito.`when`(
                serverApi?.getNames(Mockito.anyString())
            ).thenReturn(
                responseName
            )
            Assert.assertTrue(responseName.nameList.size == 2)
        }
    }

    private val responseName: ResponseName
        private get() {
            val list = ArrayList<Name>()
            val name1 = Name("Name1", "name1", "2012-06-12", "2018-06-12", "WEEKLY")
            val name2 = Name("Name2", "nam2", "2017-02-28", "2021-01-01", "MONTHLY")

            list.add(name1)
            list.add(name2)

            return ResponseName(list)
        }
}
