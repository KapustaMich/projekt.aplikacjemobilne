import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationhp.UIState
import com.example.myapplicationhp.repository.Character
import com.example.myapplicationhp.repository.HPRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private val hpRepository = HPRepository()

    private val mutableCharacterData = MutableLiveData<UIState<List<Character>>>()
    val immutableCharacterData: LiveData<UIState<List<Character>>> = mutableCharacterData

    fun getData(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val request = hpRepository.getHPDetailsResponse(id)
                if (request.isSuccessful) {
                    val characters = request.body()
                    mutableCharacterData.postValue(UIState(data = characters))
                } else {
                    mutableCharacterData.postValue(UIState(error = "${request.code()}"))
                    Log.e("MainViewModel", "123")
                }


            } catch (e: Exception) {
                mutableCharacterData.postValue(UIState(error = "Exception ${e.message}"))
                Log.e("MainViewModel", "456", e)
            }
        }
    }
}