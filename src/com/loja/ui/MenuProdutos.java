package com.loja.ui;

import com.loja.gerenciador.GerenciadorProdutos;
import com.loja.modelo.Produto;
import com.loja.exception.ProdutoException;
import com.loja.exception.ValidacaoException;

import java.util.List;
import java.util.Scanner;

public class MenuProdutos {
    private Scanner scanner = new Scanner(System.in);
    private GerenciadorProdutos gerenciador = new GerenciadorProdutos();

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n===== Sistema de Gerenciamento de Produtos =====");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Buscar Produto por ID");
            System.out.println("3. Listar Todos os Produtos");
            System.out.println("4. Atualizar Produto");
            System.out.println("5. Deletar Produto");
            System.out.println("6. Buscar por Nome");
            System.out.println("7. Buscar por Categoria");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1 -> cadastrarProduto();
                case 2 -> buscarProduto();
                case 3 -> listarProdutos();
                case 4 -> atualizarProduto();
                case 5 -> deletarProduto();
                case 6 -> buscarPorNome();
                case 7 -> buscarPorCategoria();
                case 8 -> System.out.println("Saindo do sistema...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 8);
    }

    private void cadastrarProduto() {
        try {
            String nome = lerEntradaString("Nome do Produto: ");
            double preco = lerEntradaDouble("Preço: ");
            int quantidade = lerEntradaInteira("Quantidade em Estoque: ");
            String categoria = lerEntradaString("Categoria: ");

            Produto produto = new Produto(nome, preco, quantidade, categoria);
            gerenciador.criar(produto);
            System.out.println("Produto cadastrado com sucesso!");
        } catch (ValidacaoException | ProdutoException e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    private void buscarProduto() {
        int id = lerEntradaInteira("ID do Produto: ");
        Produto produto = gerenciador.buscarPorId(id);
        if (produto != null) {
            System.out.println(produto);
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private void listarProdutos() {
        List<Produto> produtos = gerenciador.listarTodos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            System.out.println("\nLista de Produtos:");
            produtos.forEach(System.out::println);
        }
    }

    private void atualizarProduto() {
        int id = lerEntradaInteira("ID do Produto a ser atualizado: ");
        Produto produtoExistente = gerenciador.buscarPorId(id);
        if (produtoExistente != null) {
            System.out.println("Dados atuais: " + produtoExistente);
            try {
                String nome = lerEntradaString("Novo Nome (deixe em branco para manter): ");
                double preco = lerEntradaDouble("Novo Preço: ");
                int quantidade = lerEntradaInteira("Nova Quantidade em Estoque: ");
                String categoria = lerEntradaString("Nova Categoria: ");

                Produto produtoAtualizado = new Produto(nome.isEmpty() ? produtoExistente.getNome() : nome,
                        preco > 0 ? preco : produtoExistente.getPreco(),
                        quantidade >= 0 ? quantidade : produtoExistente.getQuantidadeEstoque(),
                        categoria.isEmpty() ? produtoExistente.getCategoria() : categoria);

                produtoAtualizado.setId(id);
                gerenciador.atualizar(produtoAtualizado);
                System.out.println("Produto atualizado com sucesso!");
            } catch (ValidacaoException e) {
                System.out.println("Erro ao atualizar produto: " + e.getMessage());
            }
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private void deletarProduto() {
        int id = lerEntradaInteira("ID do Produto a ser deletado: ");
        if (gerenciador.deletar(id)) {
            System.out.println("Produto deletado com sucesso!");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private void buscarPorNome() {
        String nome = lerEntradaString("Nome do Produto: ");
        List<Produto> produtos = gerenciador.buscarPorNome(nome);
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado com o nome fornecido.");
        } else {
            produtos.forEach(System.out::println);
        }
    }

    private void buscarPorCategoria() {
        String categoria = lerEntradaString("Categoria do Produto: ");
        List<Produto> produtos = gerenciador.buscarPorCategoria(categoria);
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto encontrado na categoria fornecida.");
        } else {
            produtos.forEach(System.out::println);
        }
    }

    private String lerEntradaString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    private double lerEntradaDouble(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número válido.");
            }
        }
    }

    private int lerEntradaInteira(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro válido.");
            }
        }
    }
}
