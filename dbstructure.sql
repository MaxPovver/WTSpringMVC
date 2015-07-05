# ************************************************************
# Sequel Pro SQL dump
# Версия 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Адрес: 127.0.0.1 (MySQL 5.6.23)
# Схема: worktracker
# Время создания: 2015-07-05 09:33:09 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Дамп таблицы jobs
# ------------------------------------------------------------

DROP TABLE IF EXISTS `jobs`;

CREATE TABLE `jobs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` text,
  `salary` decimal(10,0) DEFAULT NULL,
  `currency` text,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tousers` (`user_id`),
  CONSTRAINT `tousers` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `jobs` WRITE;
/*!40000 ALTER TABLE `jobs` DISABLE KEYS */;

INSERT INTO `jobs` (`id`, `name`, `salary`, `currency`, `user_id`)
VALUES
	(9,'Fund',40000,'rub',11);

/*!40000 ALTER TABLE `jobs` ENABLE KEYS */;
UNLOCK TABLES;


# Дамп таблицы logs
# ------------------------------------------------------------

DROP TABLE IF EXISTS `logs`;

CREATE TABLE `logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `start_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  `job_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tojobs` (`job_id`),
  CONSTRAINT `tojobs` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Дамп таблицы roles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `roles`;

CREATE TABLE `roles` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(11) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;

INSERT INTO `roles` (`id`, `role`)
VALUES
	(1,'ADMIN'),
	(2,'USER'),
	(3,'GUEST');

/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;


# Дамп таблицы users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(40) DEFAULT NULL,
  `password` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_unique` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;

INSERT INTO `users` (`id`, `username`, `password`)
VALUES
	(1,'max','qwerty'),
	(11,'Test','qwerty'),
	(13,'lol','epic'),
	(14,'lol2','epic'),
	(16,'admin','lol');

/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;


# Дамп таблицы users_roles
# ------------------------------------------------------------

DROP TABLE IF EXISTS `users_roles`;

CREATE TABLE `users_roles` (
  `role_id` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;

INSERT INTO `users_roles` (`role_id`, `user_id`)
VALUES
	(1,1),
	(2,11),
	(2,14),
	(2,13);

/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
