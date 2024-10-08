import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import me.jagdeep.playground.data.LastSearchCityRepository
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

class LastSearchCityRepositoryTest {

    private val dataStore: DataStore<Preferences> = mock()
    private val repository = LastSearchCityRepository(dataStore)

    @Test
    fun `getLastSearchCity returns null when no city is saved`() = runTest {
        whenever(dataStore.data).thenReturn(MutableStateFlow(emptyPreferences()))
        val city = repository.getLastSearchCity()
        assertEquals(null, city)
    }

    @Test
    fun `getLastSearchCity returns saved city`() = runTest {
        val city = "New York"
        whenever(dataStore.data).thenReturn(
            MutableStateFlow(
                preferencesOf(stringPreferencesKey("last_search_city") to city)
            )
        )

        val result = repository.getLastSearchCity()
        assertEquals(city, result)
    }

    @Test
    fun `saveLastSearchCity saves the city`() = runTest {
        val city = "Los Angeles"
        repository.saveLastSearchCity(city)
        verify(dataStore).edit(any())
    }
}
