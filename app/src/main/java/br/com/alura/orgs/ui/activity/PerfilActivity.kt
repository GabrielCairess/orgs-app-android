package br.com.alura.orgs.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import br.com.alura.orgs.R
import br.com.alura.orgs.databinding.ActivityPerfilBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class PerfilActivity : UsuarioActivityBase(), View.OnClickListener {

    private val binding by lazy {
        ActivityPerfilBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        preencheNomes()

        binding.activityPerfilSairDoApp.setOnClickListener(this)
    }

    private fun preencheNomes() {
        lifecycleScope.launch {
            usuario
                .filterNotNull()
                .collect {
                    binding.activitiyPerfilUsuarioId.text = it.id
                    binding.activitiyPerfilUsuarioNome.text = it.nome
                }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.activity_perfil_sair_do_app -> {
                lifecycleScope.launch {
                    sairDoApp()
                }
            }
        }
    }
}