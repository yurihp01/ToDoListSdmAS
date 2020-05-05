package br.edu.ifsp.scl.todolistsdm.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.scl.todolistsdm.R
import br.edu.ifsp.scl.todolistsdm.controller.TarefaActivityController
import br.edu.ifsp.scl.todolistsdm.controller.TodoListPresenter
import br.edu.ifsp.scl.todolistsdm.model.entity.Tarefa
import kotlinx.android.synthetic.main.activity_tarefa.*
import kotlinx.android.synthetic.main.toolbar.*
import splitties.toast.toast

class TarefaActivity : AppCompatActivity(), ToDoListViewInterface {
    private var tarefa: Tarefa? = null
    private lateinit var presenter: TodoListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarefa)

        toolbarTb.title = getString(R.string.tarefa)
        setSupportActionBar(toolbarTb)

        /*
        Instanciando controller
         */
        presenter = TodoListPresenter(this)

        /* Edição ou Nova? */
        tarefa = intent.getParcelableExtra(MainActivity.Constantes.TAREFA_EXTRA)
        if (tarefa != null) {
            nomeTarefaEt.setText(tarefa?.nome)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhe_tarefa, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cancelarMi -> {
                /* Inserção ou Edição cancelada pelo usuário */
                toast("${if (tarefa == null) "Inserção" else "Edição"} cancelada.")
                finish()
            }
            R.id.salvarMi -> {
                /*
                Salva ou Atualiza Tarefa
                 */
                if (tarefa == null) {
                    tarefa = Tarefa(nome = nomeTarefaEt.text.toString())
                    presenter.salvarTarefa(tarefa!!)
                }
                else {
                    tarefa?.nome = nomeTarefaEt.text.toString()
                    presenter.alterarTarefa(tarefa!!)
                }
            }
        }
        return true
    }

    override fun setTarefas(listaTarefas: MutableList<Tarefa>) {
        TODO("Not yet implemented")
    }

    override fun setRetorno(tarefa: Tarefa) {
        /*
        Retorna tarefa para MainActivity
        */
        if (tarefa != null) {
            val intentRetorno = Intent()
            intentRetorno.putExtra(
                MainActivity.Constantes.TAREFA_EXTRA,
                tarefa
            )
            setResult(Activity.RESULT_OK, intentRetorno)
        }
        finish()
    }
}
