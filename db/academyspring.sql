-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 18/03/2026 às 13:34
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `academyspring`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `aluno`
--

CREATE TABLE `aluno` (
  `id` int(11) NOT NULL,
  `curso` varchar(100) NOT NULL,
  `matricula` varchar(100) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `status` varchar(100) NOT NULL,
  `turno` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `notaEnade` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `aluno`
--

INSERT INTO `aluno` (`id`, `curso`, `matricula`, `nome`, `status`, `turno`, `email`, `notaEnade`) VALUES
(8, 'ADMINISTRACAO', 'ACA2026877', 'Caça Rato', 'INATIVO', 'MATUTINO', 'teste@teste.com', 0),
(9, 'ADMINISTRACAO', 'ACA2026278', 'Abel ', 'ATIVO', 'MATUTINO', 'abel@abel.com', 0),
(10, 'BIOMEDICINA', 'ACA2026478', 'teste', 'ATIVO', 'MATUTINO', 'teste@123.teste', 0),
(11, 'ENFERMAGEM', 'ACA2026422', 'Caim ', 'ATIVO', 'MATUTINO', 'caim@caim.com', 0);

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuario`
--

CREATE TABLE `usuario` (
  `id` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `senha` varchar(100) NOT NULL,
  `user` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `usuario`
--

INSERT INTO `usuario` (`id`, `email`, `senha`, `user`) VALUES
(1, 'teste@123', 'e10adc3949ba59abbe56e057f20f883e', 'Miro'),
(2, 'quer@o', '202cb962ac59075b964b07152d234b70', 'Miro'),
(3, 'teste@teste.com', 'e10adc3949ba59abbe56e057f20f883e', 'Marcos');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `aluno`
--
ALTER TABLE `aluno`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `aluno`
--
ALTER TABLE `aluno`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de tabela `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
