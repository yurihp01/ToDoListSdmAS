package br.edu.ifsp.scl.todolistsdm.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.room.Room
import br.edu.ifsp.scl.todolistsdm.model.dao.TarefaDao
import br.edu.ifsp.scl.todolistsdm.model.database.ToDoListDatabase
import br.edu.ifsp.scl.todolistsdm.model.entity.Tarefa
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ToDoListViewModel (context: Context): ViewModel() {
    private val model: TarefaDao = Room.databaseBuilder(
        context,
        ToDoListDatabase::class.java,
        ToDoListDatabase.Constantes.DB_NAME
    ).build().getTarefaDao()

    fun buscarTarefas(): LiveData<MutableList<Tarefa>> {
        return model.recuperarTarefas()
    }

    fun buscarTarefa(tarefaId: Int): LiveData<Tarefa> {
        return model.recuperaTarefa(tarefaId)
    }

    fun apagarTarefa(tarefa: Tarefa) {
        GlobalScope.launch {
            model.removerTarefa(tarefa)
        }
    }

    fun apagarTarefas(vararg  tarefa: Tarefa) {
        GlobalScope.launch {
            model.removerTarefas(*tarefa)
        }
    }

    /* Usando a função LiveData para chamar a função Suspend e criar um LiveData */
    fun salvarTarefa(tarefa: Tarefa): LiveData<Long> {
        return liveData {
            emit(model.inserirTarefa(tarefa))
        }
    }

    fun atualizarTarefa(tarefa: Tarefa) {
        GlobalScope.launch {
            model.atualizarTarefa(tarefa)
        }
    }
}