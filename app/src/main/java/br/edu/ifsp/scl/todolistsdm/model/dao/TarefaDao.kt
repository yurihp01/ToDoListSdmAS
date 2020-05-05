package br.edu.ifsp.scl.todolistsdm.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.edu.ifsp.scl.todolistsdm.model.entity.Tarefa

@Dao
interface TarefaDao {
    @Insert
    suspend fun inserirTarefa(tarefa: Tarefa): Long

    @Delete
    fun removerTarefa(tarefa: Tarefa)

    @Delete
    fun removerTarefas(vararg tarefa: Tarefa)

    @Update
    fun atualizarTarefa(tarefa: Tarefa)

    @Query( "SELECT * FROM tarefa")
     fun recuperarTarefas(): LiveData<MutableList<Tarefa>>

    @Query( "SELECT * FROM tarefa WHERE id = :tarefaId")
     fun recuperaTarefa(tarefaId: Int): LiveData<Tarefa>
}