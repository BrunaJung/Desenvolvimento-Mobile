package com.ti4all.agendaapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ti4all.agendaapp.data.Agenda
import kotlinx.coroutines.flow.Flow

@Dao
interface AgendaDao {

    @Insert
    suspend fun inserir(contato: Agenda)

    @Query("SELECT * FROM agenda")
    suspend fun listarTodos() : List<Agenda>

    @Query("DELETE FROM agenda WHERE id = :id")
    suspend fun deletar(id: Int)

    @Query("SELECT * from agenda WHERE id = :id")
    fun buscarPorId(id: Int): Flow<Agenda>

    @Update
    suspend fun editar(contato: Agenda)
}