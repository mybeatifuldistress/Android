package com.example.myapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val TAG = "Lab4"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                Lab4App(
                    onExit = {
                        finishAffinity()
                    }
                )
            }
        }
    }
}

@Composable
fun Lab4App(onExit: () -> Unit) {

    var screen by rememberSaveable {
        mutableStateOf("home")
    }

    var login by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var loginMessage by rememberSaveable {
        mutableStateOf("")
    }

    var registerLogin by rememberSaveable {
        mutableStateOf("")
    }

    var registerPassword by rememberSaveable {
        mutableStateOf("")
    }

    var fullName by rememberSaveable {
        mutableStateOf("")
    }

    var birthDate by rememberSaveable {
        mutableStateOf("")
    }

    var gender by rememberSaveable {
        mutableStateOf("Мужской")
    }

    var registrationMessage by rememberSaveable {
        mutableStateOf("")
    }

    val users = remember {
        mutableStateListOf("Ivan Ivanov")
    }

    when (screen) {

        "home" -> HomeScreen(
            onHello = {
                screen = "hello"
                Log.i(TAG, "Открыт экран Hello Compose")
            },
            onLogin = {
                screen = "login"
                Log.i(TAG, "Открыт экран входа")
            },
            onRegistration = {
                screen = "registration"
                Log.i(TAG, "Открыта регистрация")
            },
            onUsers = {
                screen = "users"
                Log.i(TAG, "Открыт список пользователей")
            },
            onExit = onExit
        )

        "hello" -> HelloScreen(
            onBack = {
                screen = "home"
                Log.i(TAG, "Возврат на главный экран")
            }
        )

        "login" -> LoginScreen(
            login = login,
            onLoginChange = {
                login = it
                loginMessage = ""
            },
            password = password,
            onPasswordChange = {
                password = it
                loginMessage = ""
            },
            message = loginMessage,
            onLogin = {
                if (login.isBlank() || password.isBlank()) {
                    loginMessage = "Введите логин и пароль"
                    Log.e(TAG, "Не заполнены данные для входа")
                } else {
                    loginMessage = ""
                    screen = "menu"
                    Log.i(TAG, "Выполнена демонстрация входа")
                }
            },
            onRegistration = {
                screen = "registration"
            },
            onBack = {
                screen = "home"
            },
            onExit = onExit
        )

        "registration" -> RegistrationScreen(
            login = registerLogin,
            onLoginChange = {
                registerLogin = it
            },
            password = registerPassword,
            onPasswordChange = {
                registerPassword = it
            },
            fullName = fullName,
            onFullNameChange = {
                fullName = it
            },
            birthDate = birthDate,
            onBirthDateChange = {
                birthDate = it
            },
            gender = gender,
            onGenderChange = {
                gender = it
            },
            message = registrationMessage,
            onChooseAvatar = {
                Log.i(TAG, "Нажата кнопка выбора аватара")
            },
            onRegister = {
                if (
                    registerLogin.isBlank() ||
                    registerPassword.isBlank() ||
                    fullName.isBlank()
                ) {
                    registrationMessage =
                        "Заполните логин, пароль и ФИО"
                } else {
                    login = registerLogin.trim()
                    password = registerPassword

                    val cleanName = fullName.trim()

                    if (cleanName !in users) {
                        users.add(cleanName)
                    }

                    registrationMessage = ""
                    loginMessage = ""
                    screen = "login"

                    Log.i(TAG, "Выполнена демонстрация регистрации")
                }
            },
            onBack = {
                screen = "login"
            }
        )

        "menu" -> MenuScreen(
            onProfile = {
                screen = "profile"
            },
            onSettings = {
                screen = "settings"
            },
            onUsers = {
                screen = "users"
            },
            onBack = {
                screen = "login"
            }
        )

        "profile" -> ProfileScreen(
            login = login,
            fullName = fullName,
            birthDate = birthDate,
            gender = gender,
            onBack = {
                screen = "menu"
            }
        )

        "settings" -> SettingsScreen(
            onBack = {
                screen = "menu"
            }
        )

        "users" -> UsersScreen(
            users = users,
            onAddUser = { name ->
                users.add(name)
                Log.i(TAG, "Добавлен пользователь: $name")
            },
            onBack = {
                screen = "home"
            }
        )

        else -> HomeScreen(
            onHello = { screen = "hello" },
            onLogin = { screen = "login" },
            onRegistration = { screen = "registration" },
            onUsers = { screen = "users" },
            onExit = onExit
        )
    }
}

@Composable
private fun HomeScreen(
    onHello: () -> Unit,
    onLogin: () -> Unit,
    onRegistration: () -> Unit,
    onUsers: () -> Unit,
    onExit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(150.dp))
        Text(
            text = "Начальное окно",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onHello,
            modifier = Modifier.width(280.dp).fillMaxHeight()
        ) {
            Text("Hello Compose")
        }

        Button(
            onClick = onLogin,
            modifier = Modifier.width(280.dp).fillMaxHeight()
        ) {
            Text("Вход")
        }

        Button(
            onClick = onRegistration,
            modifier = Modifier.width(280.dp).fillMaxHeight()
        ) {
            Text("Регистрация")
        }

        Button(
            onClick = onUsers,
            modifier = Modifier.width(280.dp).fillMaxHeight()
        ) {
            Text("Список пользователей")
        }

        OutlinedButton(
            onClick = onExit,
            modifier = Modifier.width(280.dp).fillMaxHeight()
        ) {
            Text("Выход")
        }
    }
}

@Composable
private fun HelloScreen(
    onBack: () -> Unit
) {
    val elements = listOf(
        "Element 0",
        "Element 1",
        "Element 2",
        "Element 3",
        "Element 4",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello Compose",
            style = TextStyle(
                fontSize = 28.sp,
                color = Color.Cyan,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
                .background(Color.LightGray)
                .padding(30.dp)
        )

        Text(
            text = "Нажми на элементы",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            itemsIndexed(elements) { index, element ->
                ClickableElement(
                    name = element,
                    index = index
                )
            }
        }
        TextButton(onClick = onBack) {
            Text("Назад")
        }
    }
}

@Composable
private fun ClickableElement(
    name: String,
    index: Int
) {
    var clicks by rememberSaveable(name) {
        mutableIntStateOf(0)
    }

    Text(
        text = "$name clicked: $clicks",
        style = TextStyle(
            fontSize = 20.sp,
            color = Color.Cyan,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(5.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(
                width = 2.dp,
                color = Color.Yellow,
                shape = CircleShape
            )
            .clickable {
                clicks++
                Log.i(TAG, "Нажат элемент $index")
            }
    )
}

@Composable
private fun LoginScreen(
    login: String,
    onLoginChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    message: String,
    onLogin: () -> Unit,
    onRegistration: () -> Unit,
    onBack: () -> Unit,
    onExit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(130.dp))
        Text(
            text = "Вход",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = login,
            onValueChange = onLoginChange,
            label = { Text("Логин") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Пароль") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            singleLine = true
        )

        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = onLogin,
            modifier = Modifier.width(280.dp).fillMaxHeight()
        ) {
            Text("Войти")
        }

        OutlinedButton(
            onClick = onRegistration,
            modifier = Modifier.width(280.dp).fillMaxHeight()
        ) {
            Text("Регистрация")
        }

        TextButton(onClick = onBack) {
            Text("На главный экран")
        }

        TextButton(onClick = onExit) {
            Text("Выход")
        }
    }
}

@Composable
private fun RegistrationScreen(
    login: String,
    onLoginChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    fullName: String,
    onFullNameChange: (String) -> Unit,
    birthDate: String,
    onBirthDateChange: (String) -> Unit,
    gender: String,
    onGenderChange: (String) -> Unit,
    message: String,
    onChooseAvatar: () -> Unit,
    onRegister: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        Text(
            text = "Регистрация",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = login,
            onValueChange = onLoginChange,
            label = { Text("Логин") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Пароль") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = fullName,
            onValueChange = onFullNameChange,
            label = { Text("ФИО") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = birthDate,
            onValueChange = onBirthDateChange,
            label = { Text("Дата рождения") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            singleLine = true
        )

        Text(
            text = "Пол",
            fontSize = 18.sp
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = gender == "Мужской",
                onClick = {
                    onGenderChange("Мужской")
                }
            )
            Text("Мужской")

            Spacer(modifier = Modifier.width(8.dp))

            RadioButton(
                selected = gender == "Женский",
                onClick = {
                    onGenderChange("Женский")
                }
            )
            Text("Женский")
        }

        Text(
            text = "👤",
            fontSize = 64.sp
        )

        OutlinedButton(
            onClick = onChooseAvatar
        ) {
            Text("Выбрать аватар")
        }

        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = onRegister,
            modifier = Modifier.width(280.dp).fillMaxHeight()
        ) {
            Text("Зарегистрироваться")
        }

        TextButton(onClick = onBack) {
            Text("Назад")
        }
    }
}

@Composable
private fun MenuScreen(
    onProfile: () -> Unit,
    onSettings: () -> Unit,
    onUsers: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(180.dp))
        Text(
            text = "Главное меню",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Button(
            onClick = onProfile,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Профиль")
        }

        Button(
            onClick = onSettings,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Настройки")
        }

        Button(
            onClick = onUsers,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Список пользователей")
        }

        TextButton(onClick = onBack) {
            Text("Назад ко входу")
        }
    }
}

@Composable
private fun ProfileScreen(
    login: String,
    fullName: String,
    birthDate: String,
    gender: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Профиль пользователя",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Text("Логин: ${login.ifBlank { "не указан" }}")
        Text("ФИО: ${fullName.ifBlank { "не указано" }}")
        Text("Дата рождения: ${birthDate.ifBlank { "не указана" }}")
        Text("Пол: $gender")

        Button(onClick = onBack) {
            Text("Назад")
        }
    }
}

@Composable
private fun SettingsScreen(
    onBack: () -> Unit
) {
    var notificationsOn by rememberSaveable {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Настройки",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Уведомления")

            Switch(
                checked = notificationsOn,
                onCheckedChange = {
                    notificationsOn = it
                    Log.i(TAG, "Уведомления: $it")
                }
            )
        }

        Text(
            text = if (notificationsOn) {
                "Уведомления включены"
            } else {
                "Уведомления выключены"
            }
        )

        Button(onClick = onBack) {
            Text("Назад")
        }
    }
}

@Composable
private fun UsersScreen(
    users: List<String>,
    onAddUser: (String) -> Unit,
    onBack: () -> Unit
) {
    var newName by rememberSaveable {
        mutableStateOf("")
    }

    var error by rememberSaveable {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        TextButton(onClick = onBack) {
            Text("Назад")
        }

        Text(
            text = "Список пользователей",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(users) { index, user ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Text(
                        text = "${index + 1}. $user",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 18.sp
                    )
                }
            }
        }

        OutlinedTextField(
            value = newName,
            onValueChange = {
                newName = it
                error = ""
            },
            label = {
                Text("Имя пользователя")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = error.isNotEmpty()
        )

        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = {
                val cleanName = newName.trim()

                if (cleanName.isEmpty()) {
                    error = "Введите имя пользователя"
                } else {
                    onAddUser(cleanName)
                    newName = ""
                    error = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Добавить пользователя")
        }
    }
}