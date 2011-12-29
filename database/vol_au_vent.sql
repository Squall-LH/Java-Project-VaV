-- phpMyAdmin SQL Dump
-- version 3.4.7
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Ven 11 Novembre 2011 à 16:24
-- Version du serveur: 5.5.17
-- Version de PHP: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `vav`
--

-- --------------------------------------------------------

--
-- Structure de la table `airport`
--

CREATE TABLE IF NOT EXISTS `airport` (
  `id_airport` int(11) NOT NULL AUTO_INCREMENT,
  `name_airport` varchar(100) NOT NULL,
  PRIMARY KEY (`id_airport`),
  UNIQUE KEY `name_airport` (`name_airport`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `airport`
--

INSERT INTO `airport` (`id_airport`, `name_airport`) VALUES
(2, 'Newark Liberty International (EWR)'),
(1, 'Paris Charles de Gaulle (CDG)');

-- --------------------------------------------------------

--
-- Structure de la table `flight`
--

CREATE TABLE IF NOT EXISTS `flight` (
  `id_flight` int(11) NOT NULL AUTO_INCREMENT,
  `id_airport_depart_flight` int(11) NOT NULL,
  `id_airport_arrival_flight` int(11) NOT NULL,
  `id_plane_flight` int(11) NOT NULL,
  `date_flight` int(11) NOT NULL,
  PRIMARY KEY (`id_flight`),
  UNIQUE KEY `id_airport_depart_flight` (`id_airport_depart_flight`,`id_airport_arrival_flight`,`id_plane_flight`),
  KEY `id_airport_arrival_flight` (`id_airport_arrival_flight`),
  KEY `id_plane_flight` (`id_plane_flight`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `plane`
--

CREATE TABLE IF NOT EXISTS `plane` (
  `id_plane` int(11) NOT NULL AUTO_INCREMENT,
  `seats_plane` int(11) NOT NULL,
  `name_plane` varchar(100) NOT NULL,
  PRIMARY KEY (`id_plane`),
  KEY `name_plane` (`name_plane`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE IF NOT EXISTS `reservation` (
  `id_reservation` int(11) NOT NULL AUTO_INCREMENT,
  `id_flight_outbound_reservation` int(11) NOT NULL,
  `id_flight_return_reservation` int(11) NOT NULL,
  `id_user_reservation` int(11) NOT NULL,
  `date_reservation` int(11) NOT NULL,
  PRIMARY KEY (`id_reservation`),
  UNIQUE KEY `id_flight_outbound_reservation` (`id_flight_outbound_reservation`,`id_flight_return_reservation`),
  UNIQUE KEY `id_user_reservation` (`id_user_reservation`),
  KEY `id_flight_return_reservation` (`id_flight_return_reservation`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `last_name_user` varchar(100) NOT NULL,
  `first_name_user` varchar(100) NOT NULL,
  `login_user` varchar(50) NOT NULL,
  `pass_user` varchar(41) NOT NULL,
  `flights_user` int(11) NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `id_reservation_user` (`login_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `flight`
--
ALTER TABLE `flight`
  ADD CONSTRAINT `flight_ibfk_3` FOREIGN KEY (`id_plane_flight`) REFERENCES `plane` (`id_plane`),
  ADD CONSTRAINT `flight_ibfk_1` FOREIGN KEY (`id_airport_depart_flight`) REFERENCES `airport` (`id_airport`),
  ADD CONSTRAINT `flight_ibfk_2` FOREIGN KEY (`id_airport_arrival_flight`) REFERENCES `airport` (`id_airport`);

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_ibfk_3` FOREIGN KEY (`id_user_reservation`) REFERENCES `user` (`id_user`),
  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`id_flight_outbound_reservation`) REFERENCES `flight` (`id_flight`),
  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`id_flight_return_reservation`) REFERENCES `flight` (`id_flight`);
