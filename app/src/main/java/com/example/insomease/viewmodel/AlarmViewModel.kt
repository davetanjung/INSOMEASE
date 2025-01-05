import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.insomease.repositories.AlarmRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AlarmViewModel(private val repository: AlarmRepository) : ViewModel() {

    // Waktu saat ini
    private val _currentTime = MutableStateFlow("00:00")
    val currentTime: StateFlow<String> = _currentTime.asStateFlow()

    // Data alarm
    val alarmTime: StateFlow<String> = repository.getAlarm().map { it.time }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = "00:00"
    )

    // Status alarm aktif atau tidak
    private val _isAlarmTriggered = MutableStateFlow(false)
    val isAlarmTriggered: StateFlow<Boolean> = _isAlarmTriggered.asStateFlow()

    init {
        // Jalankan pemantauan waktu saat ini
        monitorCurrentTime()
    }

    @SuppressLint("NewApi")
    private fun monitorCurrentTime() {
        viewModelScope.launch {
            while (true) {
                delay(1000L) // Perbarui setiap detik
                val now = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                _currentTime.value = now

                // Cek apakah alarm harus dipicu
                if (repository.getAlarm().value.isActive && now == repository.getAlarm().value.time) {
                    _isAlarmTriggered.value = true
                }
            }
        }
    }

    fun dismissAlarm() {
        _isAlarmTriggered.value = false
        repository.dismissAlarm()
    }

    fun snoozeAlarm(snoozeMinutes: Int = 5) {
        _isAlarmTriggered.value = false
        repository.snoozeAlarm(snoozeMinutes)
    }

    fun setAlarm(time: String) {
        repository.setAlarm(time)
    }
}
