package com.example.pamfirebase.Model


data class Mahasiswa(
    val nim: String,
    val nama: String,
    val alamat: String,
    val jenis_kelamin: String,
    val kelas: String,
    val angkatan: String,
    val judul_skripsi: String,
    val dosen1: String,
    val dosen2: String
) {
    constructor(

    ):this("", "", "", "", "", "", "", "", "")
}