package com.pricelinetest

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import com.pricelinetest.data.DataRepository
import com.pricelinetest.features.home.HomeViewModel
import com.pricelinetest.network.ResultWrapper
import com.pricelinetest.models.Name
import com.pricelinetest.network.apimodels.response.ResponseName
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private lateinit var viewModel: HomeViewModel
    private lateinit var application: Application
    private lateinit var repository: DataRepository
    private val lifecycleOwner: LifecycleOwner = Mockito.mock(LifecycleOwner::class.java)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this);
        application = Mockito.mock(Application::class.java)
        repository = Mockito.mock(DataRepository::class.java)
        viewModel = HomeViewModel(application, repository)
    }

    @Test
    fun fetchNameList() {
        GlobalScope.launch {
            Mockito.`when`(
                repository.getNameList(Mockito.anyString())
            ).thenReturn(
                responseName
            )
        }
        viewModel.getNameList()
        viewModel.nameList.observe(lifecycleOwner) {
            Assert.assertTrue(it.size >= 2) //headers is considered
        }
    }

    @Test
    fun countHeaders() {
        GlobalScope.launch {
            Mockito.`when`(
                repository.getNameList(Mockito.anyString())
            ).thenReturn(
                responseName
            )
            viewModel.getNameList()

            var headerCount = 0
            viewModel.nameList.observe(lifecycleOwner) { it ->
                it.forEach {
                    if (it.isHeader) {
                        headerCount++
                    }
                }
            }
            Assert.assertTrue(headerCount == 2) //one for weekly and one for monthly
        }
    }

    private val responseName: ResultWrapper<ResponseName>
        private get() {
            val list = ArrayList<Name>()
            val name1 = Name("Name1", "name1", "2012-06-12", "2018-06-12", "WEEKLY")
            val name2 = Name("Name2", "nam2", "2017-02-28", "2021-01-01", "MONTHLY")

            list.add(name1)
            list.add(name2)

            return ResultWrapper.Success(ResponseName(list))
        }
}
