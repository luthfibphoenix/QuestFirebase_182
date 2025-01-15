package com.example.pamfirebase.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pamfirebase.ui.viewmodel.FormErrorState
import com.example.pamfirebase.ui.viewmodel.FormState
import com.example.pamfirebase.ui.viewmodel.HomeUiState
import com.example.pamfirebase.ui.viewmodel.HomeViewModel
import com.example.pamfirebase.ui.viewmodel.InsertUiState
import com.example.pamfirebase.ui.viewmodel.InsertViewModel
import com.example.pamfirebase.ui.viewmodel.MahasiswaEvent
import com.example.pamfirebase.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertMhsView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState
    val uiEvent = viewModel.uiEvent
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState) {
        when (uiState){
            is FormState.Success -> {
                println(
                    "InsertMhsView: uiState is FormState.Success, navigate to home" + uiState.message
                )
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.message)
                }
                delay(700)
                onNavigate()
                viewModel.resetSnackBarMessage()
            }
            is FormState.Error -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.message)
                }
            }
            else -> Unit
        }
    }
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Tambah Mahasiswa") },
                navigationIcon = {
                    Button(onClick = onBack) {
                        Text("Back")
                    }
                }
            )
        }
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){
            InsertBodyMhs(
                uiState = uiEvent,
                homeUiState = uiState,
                onValueChange = {updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    if (viewModel.validateFields()){
                        viewModel.insertMhs()
                    }
                }
            )
        }
    }
}


@Composable
fun InsertBodyMhs(
    modifier: Modifier = Modifier,
    onValueChange: (MahasiswaEvent) -> Unit,
    uiState: InsertUiState,
    onClick: () -> Unit,
    homeUiState : FormState
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.isEntryValid.isValid()
        ) {
            if (homeUiState is FormState.Loading){
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier
                        .size(15.dp)
                        .padding(end = 5.dp)
                )
                Text(text = "Loading")
            } else {
                Text(text = "Insert")
            }
        }
        FormMahasiswa(
            mahasiswaEvent = uiState.insertUiEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun FormMahasiswa(
    mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    onValueChange: (MahasiswaEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    val jenis_kelamin = listOf("Laki-laki", "Perempuan")
    val kelas = listOf("A", "B", "C", "D", "E")

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.judul_skripsi,
            onValueChange = { newValue ->
                onValueChange(mahasiswaEvent.copy(judul_skripsi = newValue))
            },
            label = { Text("Judul") },
            isError = errorState.judul_skripsi != null,
            placeholder = { Text("Masukkan Judul") },
        )
        Text(
            text = errorState.judul_skripsi ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nama,
            onValueChange = { newValue ->
                onValueChange(mahasiswaEvent.copy(nama = newValue))
            },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan nama") },
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nim,
            onValueChange = { newValue ->
                onValueChange(mahasiswaEvent.copy(nim = newValue))
            },
            label = { Text("NIM") },
            isError = errorState.nim != null,
            placeholder = { Text("Masukkan NIM") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.nim ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis Kelamin")
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            jenis_kelamin.forEach { jk ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = mahasiswaEvent.jenis_kelamin == jk,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(jenis_kelamin = jk))
                        },
                    )
                    Text(
                        text = jk,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
        Text(
            text = errorState.jenis_kelamin ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = mahasiswaEvent.alamat,
            onValueChange = { newValue ->
                onValueChange(mahasiswaEvent.copy(alamat = newValue))
            },
            label = { Text("Alamat") },
            isError = errorState.alamat != null,
            placeholder = { Text("Masukkan alamat") },
        )
        Text(
            text = errorState.alamat ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Kelas")
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            kelas.forEach { kelas ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = mahasiswaEvent.kelas == kelas,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(kelas = kelas))
                        },
                    )
                    Text(
                        text = kelas,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
        Text(
            text = errorState.kelas ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.angkatan,
            onValueChange = { newValue ->
                onValueChange(mahasiswaEvent.copy(angkatan = newValue))
            },
            label = { Text("Angkatan") },
            isError = errorState.angkatan != null,
            placeholder = { Text("Masukkan angkatan") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.angkatan ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = mahasiswaEvent.dosen1,
            onValueChange = { newValue ->
                onValueChange(mahasiswaEvent.copy(dosen1 = newValue))
            },
            label = { Text("Dosen 1") },
            isError = errorState.dosen1 != null,
            placeholder = { Text("Masukkan Nama Dosen") },
        )
        Text(
            text = errorState.dosen1 ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = mahasiswaEvent.dosen2,
            onValueChange = { newValue ->
                onValueChange(mahasiswaEvent.copy(dosen2 = newValue))
            },
            label = { Text("Dosen 2") },
            isError = errorState.dosen2 != null,
            placeholder = { Text("Masukkan Nama Dosen") },
        )
        Text(
            text = errorState.dosen2 ?: "",
            color = Color.Red
        )
    }
}

