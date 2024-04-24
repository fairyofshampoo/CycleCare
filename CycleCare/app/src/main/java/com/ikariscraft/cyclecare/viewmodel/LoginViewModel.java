package com.ikariscraft.cyclecare.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.ikariscraft.cyclecare.repository.LoginRepository;
import com.ikariscraft.cyclecare.utilities.PasswordUtilities;

public class LoginViewModel extends AndroidViewModel {

    private LoginRepository repository;
    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<Integer> loginResult = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isUsernameValid = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isPasswordValid = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        //repository = new LoginRepository(database);
    }

    // Obtiene el LiveData para el nombre de usuario
    public LiveData<String> getUsername() {
        return username;
    }

    // Obtiene el LiveData para la contraseña
    public LiveData<String> getPassword() {
        return password;
    }

    // Obtiene el LiveData para el resultado del inicio de sesión
    public LiveData<Integer> getLoginResult() {
        return loginResult;
    }

    // Obtiene el LiveData para la validez del nombre de usuario
    public LiveData<Boolean> getIsUsernameValid() {
        return isUsernameValid;
    }

    // Obtiene el LiveData para la validez de la contraseña
    public LiveData<Boolean> getIsPasswordValid() {
        return isPasswordValid;
    }

    // Método para iniciar sesión
    public void login() {
        // Obtén el nombre de usuario y la contraseña
        String user = username.getValue();
        String pass = password.getValue();

        // Valida los datos del formulario
        if (user == null || pass == null || !isUsernameValid.getValue() || !isPasswordValid.getValue()) {
            // Si los datos del formulario no son válidos, establece el resultado del inicio de sesión como 0 (error)
            loginResult.setValue(0);
            return;
        }

        // Hashea la contraseña antes de enviarla al repositorio
        String hashedPassword = PasswordUtilities.computeSHA256Hash(pass);

        // Realiza el inicio de sesión utilizando el repositorio
        int result = repository.login(user, hashedPassword);

        // Establece el resultado del inicio de sesión
        loginResult.setValue(result);
    }

    // Método para validar los datos del formulario mientras el usuario está escribiendo
    public void loginDataChanged() {
        String usernameChanged = username.getValue();
        String passwordChanged = password.getValue();

        // Valida el nombre de usuario
        isUsernameValid.setValue(isUsernameValid(usernameChanged));

        // Valida la contraseña
        isPasswordValid.setValue(isPasswordValid(passwordChanged));
    }

    // Función para validar el nombre de usuario
    private boolean isUsernameValid(String username) {
        // Implementa tus criterios de validación aquí, por ejemplo, longitud mínima
        return username.length() >= 5;
    }

    // Función para validar la contraseña
    private boolean isPasswordValid(String password) {
        // Implementa tus criterios de validación aquí, por ejemplo, longitud mínima
        return password.length() >= 6;
    }
}
