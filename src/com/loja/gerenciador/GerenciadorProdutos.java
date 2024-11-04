package com.loja.gerenciador;

import com.loja.modelo.Produto;
import com.loja.exception.ValidacaoException;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorProdutos {
    private List<Produto> produtos = new ArrayList<>();
    private int proximoId = 1;

    // Método para criar e adicionar um novo produto
    public void criar(Produto produto) {
        validarProduto(produto);
        produto.setId(proximoId++);
        produtos.add(produto);
    }

    // Método para buscar produto por ID
    public Produto buscarPorId(int id) {
        return produtos.stream()
                .filter(produto -> produto.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Método para listar todos os produtos
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos); // Evitar exposição da lista interna
    }

    // Método para atualizar um produto existente
    public boolean atualizar(Produto produtoAtualizado) {
        Produto produtoExistente = buscarPorId(produtoAtualizado.getId());
        if (produtoExistente != null) {
            produtoExistente.setNome(produtoAtualizado.getNome());
            produtoExistente.setPreco(produtoAtualizado.getPreco());
            produtoExistente.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
            produtoExistente.setCategoria(produtoAtualizado.getCategoria());
            return true;
        }
        return false;
    }

    // Método para deletar um produto pelo ID
    public boolean deletar(int id) {
        return produtos.removeIf(produto -> produto.getId() == id);
    }

    // Método para buscar produtos por nome (case insensitive)
    public List<Produto> buscarPorNome(String nome) {
        List<Produto> resultados = new ArrayList<>();
        for (Produto produto : produtos) {
            if (produto.getNome().equalsIgnoreCase(nome)) {
                resultados.add(produto);
            }
        }
        return resultados;
    }

    // Método para buscar produtos por categoria (case insensitive)
    public List<Produto> buscarPorCategoria(String categoria) {
        List<Produto> resultados = new ArrayList<>();
        for (Produto produto : produtos) {
            if (produto.getCategoria().equalsIgnoreCase(categoria)) {
                resultados.add(produto);
            }
        }
        return resultados;
    }

    // Método privado para validar dados do produto antes da criação
    private void validarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            throw new ValidacaoException("O nome do produto não pode ser vazio.");
        }
        if (produto.getPreco() <= 0) {
            throw new ValidacaoException("O preço do produto deve ser positivo.");
        }
        if (produto.getQuantidadeEstoque() < 0) {
            throw new ValidacaoException("A quantidade em estoque não pode ser negativa.");
        }
    }
}

