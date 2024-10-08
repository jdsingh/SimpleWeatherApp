import kotlinx.coroutines.runBlocking
import me.jagdeep.playground.data.WeatherApi
import me.jagdeep.playground.data.WeatherRepository
import me.jagdeep.playground.data.fakeWeatherResponse
import me.jagdeep.playground.domain.WeatherUnit
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class WeatherRepositoryTest {

    private val weatherApi: WeatherApi = mock()
    private val apiKey = "test_api_key"
    private val repository = WeatherRepository(weatherApi, apiKey)

    @Test
    fun fetchWeatherByCity_returnsWeatherResponse() = runBlocking {
        val city = "London"
        val unit = WeatherUnit.IMPERIAL
        val expectedResponse = fakeWeatherResponse

        whenever(
            weatherApi.getWeatherByCity(city, apiKey, unit.toString())
        ).thenReturn(expectedResponse)

        val result = repository.fetchWeatherByCity(city, unit)
        assertEquals(expectedResponse, result)
    }

    @Test
    fun fetchWeatherByLocation_returnsWeatherResponse() = runBlocking {
        val lat = 51.5074
        val long = -0.1278
        val unit = WeatherUnit.METRIC
        val expectedResponse = fakeWeatherResponse

        whenever(
            weatherApi.getWeatherByLocation(lat, long, apiKey, unit.toString())
        ).thenReturn(expectedResponse)

        val result = repository.fetchWeatherByLocation(lat, long, unit)
        assertEquals(expectedResponse, result)
    }

    @Test
    fun fetchWeatherByCity_withInvalidCity_throwsException(): Unit = runBlocking {
        val city = "InvalidCity"
        val unit = WeatherUnit.METRIC

        whenever(
            weatherApi.getWeatherByCity(city, apiKey, unit.toString())
        ).thenThrow(
            RuntimeException("City not found")
        )

        try {
            repository.fetchWeatherByCity(city, unit)
        } catch (e: Exception) {
            assertEquals("City not found", e.message)
        }
    }

    @Test
    fun fetchWeatherByLocation_withInvalidCoordinates_throwsException(): Unit = runBlocking {
        val lat = -999.0
        val long = -999.0
        val unit = WeatherUnit.METRIC

        whenever(weatherApi.getWeatherByLocation(lat, long, apiKey, unit.toString())).thenThrow(
            RuntimeException("Invalid coordinates")
        )

        try {
            repository.fetchWeatherByLocation(lat, long, unit)
        } catch (e: Exception) {
            assertEquals("Invalid coordinates", e.message)
        }
    }
}
