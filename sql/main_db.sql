-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: ganglim
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `acckeyvalue`
--

DROP TABLE IF EXISTS `acckeyvalue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acckeyvalue` (
  `id` int NOT NULL DEFAULT '0',
  `key` varchar(50) NOT NULL DEFAULT '',
  `value` varchar(1000) NOT NULL DEFAULT '',
  `primary` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`primary`),
  KEY `id` (`id`),
  KEY `key` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=10291363 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `accounts`
--

DROP TABLE IF EXISTS `accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '',
  `password` varchar(128) NOT NULL DEFAULT '',
  `salt` varchar(32) DEFAULT NULL,
  `2ndpassword` varchar(134) DEFAULT NULL,
  `salt2` varchar(32) DEFAULT NULL,
  `discordid` bigint DEFAULT '0',
  `phonenumber` varchar(20) DEFAULT NULL,
  `loggedin` tinyint unsigned NOT NULL DEFAULT '0',
  `lastlogin` timestamp NULL DEFAULT NULL,
  `createdat` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `birthday` date NOT NULL DEFAULT '0000-00-00',
  `banned` tinyint(1) NOT NULL DEFAULT '0',
  `banreason` text,
  `gm` tinyint(1) NOT NULL DEFAULT '0',
  `email` tinytext,
  `macs` varchar(50) DEFAULT '00-00-00-00-00-00, 00-00-00-00',
  `tempban` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `greason` tinyint unsigned DEFAULT NULL,
  `nxCredit` varchar(45) NOT NULL DEFAULT '0',
  `ACash` int NOT NULL DEFAULT '0',
  `mPoints` int NOT NULL DEFAULT '0',
  `gender` tinyint unsigned NOT NULL DEFAULT '0',
  `SessionIP` varchar(64) DEFAULT NULL,
  `nameChange` tinyint unsigned NOT NULL DEFAULT '0',
  `points` int NOT NULL DEFAULT '0',
  `realCash` int NOT NULL DEFAULT '0',
  `e_realCash` int NOT NULL DEFAULT '-1',
  `get_e_realCash` int NOT NULL DEFAULT '0',
  `level_point` int NOT NULL DEFAULT '0',
  `hongbo_point` int NOT NULL DEFAULT '0',
  `monthvotes` int NOT NULL DEFAULT '0',
  `totalvotes` int NOT NULL DEFAULT '0',
  `lastvote` int NOT NULL DEFAULT '0',
  `lastvote2` int NOT NULL DEFAULT '0',
  `lastlogon` timestamp NULL DEFAULT NULL,
  `lastvoteip` varchar(64) DEFAULT NULL,
  `cookie` varchar(160) DEFAULT NULL,
  `serialNumber` varchar(160) DEFAULT NULL,
  `allowed` varchar(45) NOT NULL DEFAULT '0',
  `connecterClient` varchar(45) DEFAULT NULL,
  `maincharacter` mediumtext,
  `connecterIP` varchar(45) DEFAULT NULL,
  `connecterTAGON` varchar(50) DEFAULT NULL,
  `connecterTAGOFF` varchar(50) DEFAULT NULL,
  `banby` varchar(45) DEFAULT NULL,
  `mac` varchar(17) DEFAULT '00-00-00-00-00-00',
  `volume` varchar(11) DEFAULT '00-00-00-00',
  `unionLevel` int DEFAULT '0',
  `chat_ban_time` timestamp NULL DEFAULT NULL,
  `cashnumber` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `ranking1` (`id`,`banned`,`gm`),
  KEY `id` (`id`,`cookie`)
) ENGINE=InnoDB AUTO_INCREMENT=806 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `accounts_sms`
--

DROP TABLE IF EXISTS `accounts_sms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accounts_sms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `discordid` bigint DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `phonenumber` varchar(50) DEFAULT NULL,
  `smscode` varchar(50) DEFAULT NULL,
  `Date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `discordid` (`discordid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `acheck`
--

DROP TABLE IF EXISTS `acheck`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acheck` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cid` int unsigned NOT NULL,
  `keya` varchar(80) NOT NULL,
  `value` varchar(80) NOT NULL,
  `day` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `id` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `achievement_info`
--

DROP TABLE IF EXISTS `achievement_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `achievement_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_id` int NOT NULL,
  `last_week_score` int DEFAULT NULL,
  `last_week_rank` int DEFAULT NULL,
  `last_week_delta_rank` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `account_id` (`account_id`) USING BTREE,
  CONSTRAINT `FK_achievement_info_accounts` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70207 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `achievement_insignia`
--

DROP TABLE IF EXISTS `achievement_insignia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `achievement_insignia` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_id` int DEFAULT NULL,
  `grade` tinyint DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `achieve_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `account_id` (`account_id`) USING BTREE,
  CONSTRAINT `FK_achievement_insignia_accounts` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `achievement_missions`
--

DROP TABLE IF EXISTS `achievement_missions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `achievement_missions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_id` int DEFAULT '0',
  `achievement_id` int DEFAULT '0',
  `mission` tinyint DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `time` timestamp NULL DEFAULT NULL,
  `sub_mission` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `account_id` (`account_id`) USING BTREE,
  KEY `achievement_id` (`achievement_id`),
  CONSTRAINT `FK_achievement_missions_accounts` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `admin_dbver`
--

DROP TABLE IF EXISTS `admin_dbver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin_dbver` (
  `ver` int NOT NULL,
  PRIMARY KEY (`ver`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `alliances`
--

DROP TABLE IF EXISTS `alliances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alliances` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(13) NOT NULL,
  `leaderid` int NOT NULL,
  `guild1` int NOT NULL,
  `guild2` int NOT NULL,
  `guild3` int NOT NULL DEFAULT '0',
  `guild4` int NOT NULL DEFAULT '0',
  `guild5` int NOT NULL DEFAULT '0',
  `rank1` varchar(13) NOT NULL DEFAULT 'Master',
  `rank2` varchar(13) NOT NULL DEFAULT 'Jr.Master',
  `rank3` varchar(13) NOT NULL DEFAULT 'Member',
  `rank4` varchar(13) NOT NULL DEFAULT 'Member',
  `rank5` varchar(13) NOT NULL DEFAULT 'Member',
  `capacity` int NOT NULL DEFAULT '2',
  `notice` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `id` (`id`),
  KEY `leaderid` (`leaderid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `androids`
--

DROP TABLE IF EXISTS `androids`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `androids` (
  `uniqueid` bigint unsigned NOT NULL DEFAULT '0',
  `name` varchar(13) NOT NULL DEFAULT 'Android',
  `hair` int NOT NULL DEFAULT '0',
  `face` int NOT NULL DEFAULT '0',
  `skin` int NOT NULL DEFAULT '0',
  `ear` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`uniqueid`),
  KEY `uniqueid` (`uniqueid`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `artifacts`
--

DROP TABLE IF EXISTS `artifacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `artifacts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `accid` int DEFAULT NULL,
  `slot` int DEFAULT NULL,
  `upgrade` int DEFAULT NULL,
  `skillid1` int DEFAULT NULL,
  `skillid2` int DEFAULT NULL,
  `skillid3` int DEFAULT NULL,
  `time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `accid` (`accid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auction_wishlist`
--

DROP TABLE IF EXISTS `auction_wishlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auction_wishlist` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_id` int DEFAULT NULL,
  `wishlist` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=749 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auctionequipenchant`
--

DROP TABLE IF EXISTS `auctionequipenchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auctionequipenchant` (
  `inventoryequipenchantid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` bigint unsigned NOT NULL DEFAULT '0',
  `str` smallint NOT NULL DEFAULT '0',
  `dex` smallint NOT NULL DEFAULT '0',
  `int` smallint NOT NULL DEFAULT '0',
  `luk` smallint NOT NULL DEFAULT '0',
  `hp` smallint NOT NULL DEFAULT '0',
  `mp` smallint NOT NULL DEFAULT '0',
  `watk` smallint NOT NULL DEFAULT '0',
  `matk` smallint NOT NULL DEFAULT '0',
  `wdef` smallint NOT NULL DEFAULT '0',
  `mdef` smallint NOT NULL DEFAULT '0',
  `acc` smallint NOT NULL DEFAULT '0',
  `avoid` smallint NOT NULL DEFAULT '0',
  PRIMARY KEY (`inventoryequipenchantid`) USING BTREE,
  KEY `iv_idx` (`inventoryitemid`) USING BTREE,
  CONSTRAINT `FK_auctionequipenchant_copy_auctionequipment` FOREIGN KEY (`inventoryitemid`) REFERENCES `auctionequipment` (`inventoryitemid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auctionequipment`
--

DROP TABLE IF EXISTS `auctionequipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auctionequipment` (
  `inventoryequipmentid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` bigint unsigned NOT NULL DEFAULT '0',
  `player_id` int NOT NULL DEFAULT '0',
  `account_id` int NOT NULL DEFAULT '0',
  `item_id` int unsigned NOT NULL DEFAULT '0',
  `upgradeslots` tinyint unsigned NOT NULL DEFAULT '0',
  `level` tinyint unsigned NOT NULL DEFAULT '0',
  `str` smallint NOT NULL DEFAULT '0',
  `dex` smallint NOT NULL DEFAULT '0',
  `int` smallint NOT NULL DEFAULT '0',
  `luk` smallint NOT NULL DEFAULT '0',
  `arc` int NOT NULL DEFAULT '0',
  `arcexp` int NOT NULL DEFAULT '0',
  `arclevel` int NOT NULL DEFAULT '0',
  `hp` smallint NOT NULL DEFAULT '0',
  `mp` smallint NOT NULL DEFAULT '0',
  `hpR` smallint NOT NULL DEFAULT '0',
  `mpR` smallint NOT NULL DEFAULT '0',
  `watk` smallint NOT NULL DEFAULT '0',
  `matk` smallint NOT NULL DEFAULT '0',
  `wdef` smallint NOT NULL DEFAULT '0',
  `mdef` smallint NOT NULL DEFAULT '0',
  `acc` smallint NOT NULL DEFAULT '0',
  `avoid` smallint NOT NULL DEFAULT '0',
  `hands` smallint NOT NULL DEFAULT '0',
  `speed` smallint NOT NULL DEFAULT '0',
  `jump` smallint NOT NULL DEFAULT '0',
  `ViciousHammer` tinyint NOT NULL DEFAULT '0',
  `itemEXP` int NOT NULL DEFAULT '0',
  `durability` mediumint NOT NULL DEFAULT '-1',
  `enhance` tinyint NOT NULL DEFAULT '0',
  `state` tinyint NOT NULL DEFAULT '0',
  `line` tinyint NOT NULL DEFAULT '0',
  `potential1` int NOT NULL DEFAULT '0',
  `potential2` int NOT NULL DEFAULT '0',
  `potential3` int NOT NULL DEFAULT '0',
  `potential4` int NOT NULL DEFAULT '0',
  `potential5` int NOT NULL DEFAULT '0',
  `potential6` int NOT NULL DEFAULT '0',
  `fusionAnvil` int NOT NULL DEFAULT '0',
  `incSkill` int NOT NULL DEFAULT '-1',
  `charmEXP` smallint NOT NULL DEFAULT '-1',
  `pvpDamage` smallint NOT NULL DEFAULT '0',
  `enhanctBuff` int NOT NULL DEFAULT '0',
  `reqLevel` int NOT NULL DEFAULT '0',
  `yggdrasilWisdom` tinyint NOT NULL DEFAULT '0',
  `finalStrike` tinyint NOT NULL DEFAULT '0',
  `bossDamage` int NOT NULL DEFAULT '0',
  `ignorePDR` int NOT NULL DEFAULT '0',
  `totalDamage` int NOT NULL DEFAULT '0',
  `allStat` int NOT NULL DEFAULT '0',
  `karmaCount` int NOT NULL DEFAULT '-1',
  `soulname` int NOT NULL DEFAULT '0',
  `soulenchanter` int NOT NULL DEFAULT '0',
  `soulpotential` int NOT NULL DEFAULT '0',
  `soulskill` int NOT NULL DEFAULT '0',
  `fire` bigint NOT NULL DEFAULT '-1',
  `starforce` int NOT NULL DEFAULT '0',
  `itemtrace` int NOT NULL DEFAULT '0',
  `downlevel` int NOT NULL DEFAULT '0',
  `additional_enhance` int NOT NULL DEFAULT '-1',
  `sp_grade` int NOT NULL DEFAULT '0',
  `sp_attack` int NOT NULL DEFAULT '0',
  `sp_all_stat` int NOT NULL DEFAULT '0',
  `item_state_flag` int NOT NULL DEFAULT '-1',
  `csoption_grade` int NOT NULL DEFAULT '0',
  `csoption1` int NOT NULL DEFAULT '0',
  `csoption2` int NOT NULL DEFAULT '0',
  `csoption3` int NOT NULL DEFAULT '0',
  `csoption_expired` bigint NOT NULL DEFAULT '-1',
  `ex_grade_option` bigint NOT NULL DEFAULT '0',
  `CHUC` int NOT NULL DEFAULT '0',
  `check_clear` int NOT NULL DEFAULT '0',
  `special_royal` int NOT NULL DEFAULT '0',
  `serial_number` bigint NOT NULL DEFAULT '0',
  `cash_enchant_count` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`inventoryequipmentid`),
  KEY `inventoryitemid` (`inventoryitemid`),
  CONSTRAINT `auctionitems_ibfk_1` FOREIGN KEY (`inventoryitemid`) REFERENCES `auctionitems` (`inventoryitemid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=142246977 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auctionequipmentexceptional`
--

DROP TABLE IF EXISTS `auctionequipmentexceptional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auctionequipmentexceptional` (
  `inventoryequipexceptid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` bigint unsigned NOT NULL DEFAULT '0',
  `slot` smallint unsigned NOT NULL DEFAULT '0',
  `str` smallint NOT NULL DEFAULT '0',
  `dex` smallint NOT NULL DEFAULT '0',
  `int` smallint NOT NULL DEFAULT '0',
  `luk` smallint NOT NULL DEFAULT '0',
  `hp` smallint NOT NULL DEFAULT '0',
  `mp` smallint NOT NULL DEFAULT '0',
  `watk` smallint NOT NULL DEFAULT '0',
  `matk` smallint NOT NULL DEFAULT '0',
  `wdef` smallint NOT NULL DEFAULT '0',
  `mdef` smallint NOT NULL DEFAULT '0',
  `acc` smallint NOT NULL DEFAULT '0',
  `avoid` smallint NOT NULL DEFAULT '0',
  PRIMARY KEY (`inventoryequipexceptid`) USING BTREE,
  KEY `iv2_idx` (`inventoryitemid`) USING BTREE,
  CONSTRAINT `FK_auctionequipmentexceptional_auctionequipment` FOREIGN KEY (`inventoryitemid`) REFERENCES `auctionequipment` (`inventoryitemid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auctionitems`
--

DROP TABLE IF EXISTS `auctionitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auctionitems` (
  `inventoryitemid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `historyID` int DEFAULT '0',
  `characterid` int DEFAULT NULL,
  `accountid` int DEFAULT NULL,
  `packageid` int DEFAULT NULL,
  `itemid` int NOT NULL DEFAULT '0',
  `inventorytype` int NOT NULL DEFAULT '0',
  `position` int NOT NULL DEFAULT '0',
  `quantity` int NOT NULL DEFAULT '0',
  `owner` varchar(10) DEFAULT NULL,
  `GM_Log` varchar(255) DEFAULT NULL,
  `uniqueid` bigint NOT NULL DEFAULT '-1',
  `flag` int NOT NULL DEFAULT '0',
  `expiredate` bigint NOT NULL DEFAULT '-1',
  `type` tinyint(1) NOT NULL DEFAULT '0',
  `sender` varchar(13) NOT NULL DEFAULT '',
  `once_trade` int NOT NULL DEFAULT '0',
  `bid` bigint DEFAULT NULL,
  `meso` bigint DEFAULT NULL,
  `expired` bigint DEFAULT NULL,
  `bargain` tinyint(1) DEFAULT NULL,
  `ownername` varchar(20) DEFAULT NULL,
  `buyer` int DEFAULT NULL,
  `buytime` bigint DEFAULT NULL,
  `starttime` bigint DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`inventoryitemid`),
  KEY `inventorytype` (`inventorytype`),
  KEY `accountid` (`accountid`),
  KEY `characterid_2` (`characterid`),
  KEY `status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=9057 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auctions`
--

DROP TABLE IF EXISTS `auctions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auctions` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int NOT NULL,
  `inventoryid` int NOT NULL,
  `bid` bigint NOT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auth_server_channel_ip`
--

DROP TABLE IF EXISTS `auth_server_channel_ip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_server_channel_ip` (
  `channelconfigid` int unsigned NOT NULL AUTO_INCREMENT,
  `channelid` int unsigned NOT NULL DEFAULT '0',
  `name` tinytext NOT NULL,
  `value` tinytext NOT NULL,
  PRIMARY KEY (`channelconfigid`),
  KEY `channelid` (`channelid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `battlelog`
--

DROP TABLE IF EXISTS `battlelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `battlelog` (
  `battlelogid` int NOT NULL AUTO_INCREMENT,
  `accid` int NOT NULL DEFAULT '0',
  `accid_to` int NOT NULL DEFAULT '0',
  `when` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`battlelogid`),
  KEY `accid` (`accid`),
  CONSTRAINT `battlelog_ibfk_1` FOREIGN KEY (`accid`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `blacklist`
--

DROP TABLE IF EXISTS `blacklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blacklist` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `characterId` int NOT NULL DEFAULT '0',
  `b_chrName` tinytext CHARACTER SET euckr COLLATE euckr_korean_ci,
  `b_denoteName` tinytext CHARACTER SET euckr COLLATE euckr_korean_ci,
  `b_chrId` int NOT NULL DEFAULT '0',
  `b_unk` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `characterId` (`characterId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `buddies`
--

DROP TABLE IF EXISTS `buddies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `buddies` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accid` int NOT NULL,
  `buddyid` int NOT NULL,
  `buddyaccid` int NOT NULL,
  `pending` tinyint NOT NULL DEFAULT '0',
  `groupname` varchar(16) NOT NULL DEFAULT '그룹 미지정',
  `memo` text,
  PRIMARY KEY (`id`),
  KEY `accid` (`accid`)
) ENGINE=InnoDB AUTO_INCREMENT=171380 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cabinet_equipenchant`
--

DROP TABLE IF EXISTS `cabinet_equipenchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cabinet_equipenchant` (
  `inventoryequipenchantid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` bigint unsigned NOT NULL DEFAULT '0',
  `str` smallint NOT NULL DEFAULT '0',
  `dex` smallint NOT NULL DEFAULT '0',
  `int` smallint NOT NULL DEFAULT '0',
  `luk` smallint NOT NULL DEFAULT '0',
  `hp` smallint NOT NULL DEFAULT '0',
  `mp` smallint NOT NULL DEFAULT '0',
  `watk` smallint NOT NULL DEFAULT '0',
  `matk` smallint NOT NULL DEFAULT '0',
  `wdef` smallint NOT NULL DEFAULT '0',
  `mdef` smallint NOT NULL DEFAULT '0',
  `acc` smallint NOT NULL DEFAULT '0',
  `avoid` smallint NOT NULL DEFAULT '0',
  PRIMARY KEY (`inventoryequipenchantid`) USING BTREE,
  KEY `iv_idx` (`inventoryitemid`) USING BTREE,
  CONSTRAINT `FK_cabinet_equipenchant_copy_cabinet_equipment` FOREIGN KEY (`inventoryitemid`) REFERENCES `cabinet_equipment` (`inventoryitemid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cabinet_equipment`
--

DROP TABLE IF EXISTS `cabinet_equipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cabinet_equipment` (
  `inventoryequipmentid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` bigint unsigned NOT NULL DEFAULT '0',
  `player_id` int NOT NULL DEFAULT '0',
  `account_id` int NOT NULL DEFAULT '0',
  `item_id` int unsigned NOT NULL DEFAULT '0',
  `upgradeslots` tinyint unsigned NOT NULL DEFAULT '0',
  `level` tinyint unsigned NOT NULL DEFAULT '0',
  `str` smallint NOT NULL DEFAULT '0',
  `dex` smallint NOT NULL DEFAULT '0',
  `int` smallint NOT NULL DEFAULT '0',
  `luk` smallint NOT NULL DEFAULT '0',
  `arc` int NOT NULL DEFAULT '0',
  `arcexp` int NOT NULL DEFAULT '0',
  `arclevel` int NOT NULL DEFAULT '0',
  `hp` smallint NOT NULL DEFAULT '0',
  `mp` smallint NOT NULL DEFAULT '0',
  `hpR` smallint NOT NULL DEFAULT '0',
  `mpR` smallint NOT NULL DEFAULT '0',
  `watk` smallint NOT NULL DEFAULT '0',
  `matk` smallint NOT NULL DEFAULT '0',
  `wdef` smallint NOT NULL DEFAULT '0',
  `mdef` smallint NOT NULL DEFAULT '0',
  `acc` smallint NOT NULL DEFAULT '0',
  `avoid` smallint NOT NULL DEFAULT '0',
  `hands` smallint NOT NULL DEFAULT '0',
  `speed` smallint NOT NULL DEFAULT '0',
  `jump` smallint NOT NULL DEFAULT '0',
  `ViciousHammer` tinyint NOT NULL DEFAULT '0',
  `itemEXP` int NOT NULL DEFAULT '0',
  `durability` mediumint NOT NULL DEFAULT '-1',
  `enhance` tinyint NOT NULL DEFAULT '0',
  `state` tinyint NOT NULL DEFAULT '0',
  `line` tinyint NOT NULL DEFAULT '0',
  `potential1` int NOT NULL DEFAULT '0',
  `potential2` int NOT NULL DEFAULT '0',
  `potential3` int NOT NULL DEFAULT '0',
  `potential4` int NOT NULL DEFAULT '0',
  `potential5` int NOT NULL DEFAULT '0',
  `potential6` int NOT NULL DEFAULT '0',
  `fusionAnvil` int NOT NULL DEFAULT '0',
  `incSkill` int NOT NULL DEFAULT '-1',
  `charmEXP` smallint NOT NULL DEFAULT '-1',
  `pvpDamage` smallint NOT NULL DEFAULT '0',
  `enhanctBuff` int NOT NULL DEFAULT '0',
  `reqLevel` int NOT NULL DEFAULT '0',
  `yggdrasilWisdom` tinyint NOT NULL DEFAULT '0',
  `finalStrike` tinyint NOT NULL DEFAULT '0',
  `bossDamage` int NOT NULL DEFAULT '0',
  `ignorePDR` int NOT NULL DEFAULT '0',
  `totalDamage` int NOT NULL DEFAULT '0',
  `allStat` int NOT NULL DEFAULT '0',
  `karmaCount` int NOT NULL DEFAULT '-1',
  `soulname` int NOT NULL DEFAULT '0',
  `soulenchanter` int NOT NULL DEFAULT '0',
  `soulpotential` int NOT NULL DEFAULT '0',
  `soulskill` int NOT NULL DEFAULT '0',
  `fire` bigint NOT NULL DEFAULT '-1',
  `starforce` int NOT NULL DEFAULT '0',
  `itemtrace` int NOT NULL DEFAULT '0',
  `downlevel` int NOT NULL DEFAULT '0',
  `additional_enhance` int NOT NULL DEFAULT '-1',
  `sp_grade` int NOT NULL DEFAULT '0',
  `sp_attack` int NOT NULL DEFAULT '0',
  `sp_all_stat` int NOT NULL DEFAULT '0',
  `item_state_flag` int NOT NULL DEFAULT '-1',
  `csoption_grade` int NOT NULL DEFAULT '0',
  `csoption1` int NOT NULL DEFAULT '0',
  `csoption2` int NOT NULL DEFAULT '0',
  `csoption3` int NOT NULL DEFAULT '0',
  `csoption_expired` bigint NOT NULL DEFAULT '-1',
  `ex_grade_option` bigint NOT NULL DEFAULT '0',
  `CHUC` int NOT NULL DEFAULT '0',
  `check_clear` int NOT NULL DEFAULT '0',
  `special_royal` int NOT NULL DEFAULT '0',
  `serial_number` bigint NOT NULL DEFAULT '0',
  `cash_enchant_count` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`inventoryequipmentid`),
  KEY `inventoryitemid` (`inventoryitemid`),
  CONSTRAINT `cabinet_equipment_ibfk_1` FOREIGN KEY (`inventoryitemid`) REFERENCES `cabinet_items` (`inventoryitemid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=euckr ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cabinet_equipment_exceptional`
--

DROP TABLE IF EXISTS `cabinet_equipment_exceptional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cabinet_equipment_exceptional` (
  `inventoryequipexceptid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` bigint unsigned NOT NULL DEFAULT '0',
  `slot` smallint unsigned NOT NULL DEFAULT '0',
  `str` smallint NOT NULL DEFAULT '0',
  `dex` smallint NOT NULL DEFAULT '0',
  `int` smallint NOT NULL DEFAULT '0',
  `luk` smallint NOT NULL DEFAULT '0',
  `hp` smallint NOT NULL DEFAULT '0',
  `mp` smallint NOT NULL DEFAULT '0',
  `watk` smallint NOT NULL DEFAULT '0',
  `matk` smallint NOT NULL DEFAULT '0',
  `wdef` smallint NOT NULL DEFAULT '0',
  `mdef` smallint NOT NULL DEFAULT '0',
  `acc` smallint NOT NULL DEFAULT '0',
  `avoid` smallint NOT NULL DEFAULT '0',
  PRIMARY KEY (`inventoryequipexceptid`) USING BTREE,
  KEY `iv2_idx` (`inventoryitemid`) USING BTREE,
  CONSTRAINT `FK_cabinet_equip_exceptional_cabinet_equipment` FOREIGN KEY (`inventoryitemid`) REFERENCES `cabinet_equipment` (`inventoryitemid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cabinet_items`
--

DROP TABLE IF EXISTS `cabinet_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cabinet_items` (
  `inventoryitemid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int DEFAULT NULL,
  `accountid` int DEFAULT NULL,
  `packageid` int DEFAULT NULL,
  `itemid` int NOT NULL DEFAULT '0',
  `inventorytype` int NOT NULL DEFAULT '0',
  `position` int NOT NULL DEFAULT '0',
  `quantity` int NOT NULL DEFAULT '0',
  `owner` tinytext,
  `GM_Log` tinytext,
  `uniqueid` bigint NOT NULL DEFAULT '-1',
  `flag` int NOT NULL DEFAULT '0',
  `expiredate` bigint NOT NULL DEFAULT '-1',
  `type` tinyint(1) NOT NULL DEFAULT '0',
  `sender` varchar(13) NOT NULL DEFAULT '',
  `once_trade` int DEFAULT '0',
  `cabinet_index` int DEFAULT '0',
  `cabinet_expired_time` bigint DEFAULT '0',
  `cabinet_title` varchar(50) DEFAULT '',
  `cabinet_desc` tinytext,
  PRIMARY KEY (`inventoryitemid`),
  KEY `inventorytype` (`inventorytype`),
  KEY `accountid` (`accountid`),
  KEY `packageid` (`packageid`),
  KEY `characterid_2` (`characterid`,`inventorytype`)
) ENGINE=InnoDB AUTO_INCREMENT=14537 DEFAULT CHARSET=euckr ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cashshop_limit_sell`
--

DROP TABLE IF EXISTS `cashshop_limit_sell`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cashshop_limit_sell` (
  `serial` int NOT NULL,
  `amount` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`serial`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cashshop_modified_items`
--

DROP TABLE IF EXISTS `cashshop_modified_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cashshop_modified_items` (
  `serial` int NOT NULL,
  `discount_price` int NOT NULL DEFAULT '-1',
  `mark` tinyint(1) NOT NULL DEFAULT '-1',
  `showup` tinyint(1) NOT NULL DEFAULT '0',
  `itemid` int NOT NULL DEFAULT '0',
  `priority` tinyint NOT NULL DEFAULT '0',
  `package` tinyint(1) NOT NULL DEFAULT '0',
  `period` tinyint NOT NULL DEFAULT '0',
  `gender` tinyint(1) NOT NULL DEFAULT '0',
  `count` tinyint NOT NULL DEFAULT '0',
  `meso` int NOT NULL DEFAULT '0',
  `unk_1` tinyint(1) NOT NULL DEFAULT '0',
  `unk_2` tinyint(1) NOT NULL DEFAULT '0',
  `unk_3` tinyint(1) NOT NULL DEFAULT '0',
  `extra_flags` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`serial`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cashstorages`
--

DROP TABLE IF EXISTS `cashstorages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cashstorages` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accid` int DEFAULT NULL,
  `itemid` int DEFAULT NULL,
  `qty` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `charid` (`accid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ccu`
--

DROP TABLE IF EXISTS `ccu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ccu` (
  `id` bigint NOT NULL,
  `timestamp` timestamp NULL DEFAULT NULL,
  `count` int DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `character_potential_table`
--

DROP TABLE IF EXISTS `character_potential_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `character_potential_table` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `rare` int unsigned NOT NULL,
  `skill_id` int unsigned NOT NULL,
  `skill_point` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `character_slots`
--

DROP TABLE IF EXISTS `character_slots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `character_slots` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accid` int NOT NULL DEFAULT '0',
  `worldid` int NOT NULL DEFAULT '0',
  `charslots` int NOT NULL DEFAULT '6',
  PRIMARY KEY (`id`),
  KEY `accid` (`accid`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `characters`
--

DROP TABLE IF EXISTS `characters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `characters` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accountid` int NOT NULL DEFAULT '0',
  `world` tinyint(1) NOT NULL DEFAULT '0',
  `mainchr` tinyint(1) NOT NULL DEFAULT '0',
  `name` varchar(13) NOT NULL DEFAULT '',
  `level` int unsigned NOT NULL DEFAULT '0',
  `exp` bigint NOT NULL DEFAULT '0',
  `dex` int NOT NULL DEFAULT '0',
  `str` int NOT NULL DEFAULT '0',
  `luk` int NOT NULL DEFAULT '0',
  `int` int NOT NULL DEFAULT '0',
  `hp` int NOT NULL DEFAULT '0',
  `secondHp` int NOT NULL DEFAULT '0',
  `mp` int NOT NULL DEFAULT '0',
  `maxhp` int NOT NULL DEFAULT '0',
  `maxmp` int NOT NULL DEFAULT '0',
  `meso` bigint unsigned NOT NULL DEFAULT '0',
  `hpApUsed` int NOT NULL DEFAULT '0',
  `job` int NOT NULL DEFAULT '0',
  `skincolor` int NOT NULL DEFAULT '0',
  `secondSkincolor` tinyint(1) NOT NULL DEFAULT '0',
  `gender` tinyint(1) NOT NULL DEFAULT '0',
  `secondgender` tinyint(1) NOT NULL DEFAULT '0',
  `fame` int NOT NULL DEFAULT '0',
  `hair` int NOT NULL DEFAULT '0',
  `secondhair` int NOT NULL DEFAULT '0',
  `face` int NOT NULL DEFAULT '0',
  `secondface` int NOT NULL DEFAULT '0',
  `faceBaseColor` int NOT NULL DEFAULT '0',
  `faceAddColor` int NOT NULL DEFAULT '0',
  `faceBaseProb` int NOT NULL DEFAULT '0',
  `secondFaceBaseColor` int NOT NULL DEFAULT '0',
  `secondFaceAddColor` int NOT NULL DEFAULT '0',
  `secondFaceBaseProb` int NOT NULL DEFAULT '0',
  `demonMarking` int NOT NULL DEFAULT '0',
  `ap` int NOT NULL DEFAULT '0',
  `map` int NOT NULL DEFAULT '0',
  `spawnpoint` int NOT NULL DEFAULT '0',
  `gm` int NOT NULL DEFAULT '0',
  `party` int NOT NULL DEFAULT '0',
  `buddyCapacity` int NOT NULL DEFAULT '25',
  `createdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `today_loggedin_date` timestamp NOT NULL DEFAULT '1970-12-31 13:00:00',
  `last_loggedin_date` timestamp NOT NULL DEFAULT '1970-12-31 13:00:00',
  `guildid` int unsigned NOT NULL DEFAULT '0',
  `guildrank` tinyint unsigned NOT NULL DEFAULT '5',
  `allianceRank` tinyint unsigned NOT NULL DEFAULT '5',
  `guildContribution` int NOT NULL DEFAULT '0',
  `todayContribution` int DEFAULT '0',
  `lastAttendanceDate` int DEFAULT '0',
  `pets` varchar(60) NOT NULL DEFAULT '-1,-1,-1',
  `sp` varchar(40) NOT NULL DEFAULT '0,0,0,0,0,0,0,0,0,0',
  `subcategory` int NOT NULL DEFAULT '0',
  `rank` int NOT NULL DEFAULT '1',
  `rankMove` int NOT NULL DEFAULT '0',
  `jobRank` int NOT NULL DEFAULT '1',
  `jobRankMove` int NOT NULL DEFAULT '0',
  `marriageId` int NOT NULL DEFAULT '0',
  `familyid` int NOT NULL DEFAULT '0',
  `seniorid` int NOT NULL DEFAULT '0',
  `junior1` int NOT NULL DEFAULT '0',
  `junior2` int NOT NULL DEFAULT '0',
  `currentrep` int NOT NULL DEFAULT '0',
  `totalrep` int NOT NULL DEFAULT '0',
  `fatigue` tinyint NOT NULL DEFAULT '0',
  `charm` mediumint NOT NULL DEFAULT '0',
  `craft` mediumint NOT NULL DEFAULT '0',
  `charisma` mediumint NOT NULL DEFAULT '0',
  `will` mediumint NOT NULL DEFAULT '0',
  `sense` mediumint NOT NULL DEFAULT '0',
  `insight` mediumint NOT NULL DEFAULT '0',
  `today_charm` mediumint NOT NULL DEFAULT '0',
  `today_craft` mediumint NOT NULL DEFAULT '0',
  `today_charisma` mediumint NOT NULL DEFAULT '0',
  `today_will` mediumint NOT NULL DEFAULT '0',
  `today_sense` mediumint NOT NULL DEFAULT '0',
  `today_insight` mediumint NOT NULL DEFAULT '0',
  `totalWins` int NOT NULL DEFAULT '0',
  `totalLosses` int NOT NULL DEFAULT '0',
  `pvpExp` int NOT NULL DEFAULT '0',
  `pvpPoints` int NOT NULL DEFAULT '0',
  `reborns` int NOT NULL DEFAULT '0',
  `apstorage` int NOT NULL DEFAULT '0',
  `soulcount` int NOT NULL DEFAULT '0',
  `itcafetime` int NOT NULL DEFAULT '0',
  `basecolor` int NOT NULL DEFAULT '-1',
  `addcolor` int NOT NULL DEFAULT '0',
  `baseprob` int NOT NULL DEFAULT '0',
  `innerExp` int NOT NULL DEFAULT '0',
  `innerLevel` int NOT NULL DEFAULT '0',
  `wp` int NOT NULL DEFAULT '0',
  `sia` int NOT NULL DEFAULT '0',
  `hpcash` int NOT NULL DEFAULT '0',
  `hp2cash` int NOT NULL DEFAULT '0',
  `transcendence` int NOT NULL DEFAULT '0',
  `addDamage` bigint NOT NULL DEFAULT '0',
  `addDamageS` int NOT NULL DEFAULT '0',
  `killpoint` bigint NOT NULL DEFAULT '0',
  `hadm` int NOT NULL DEFAULT '0',
  `ts` int NOT NULL DEFAULT '0',
  `crown` varchar(45) NOT NULL DEFAULT '-1,-1,-1,-1,-1',
  `betaClothes` int NOT NULL DEFAULT '0',
  `mesoChairCount` int NOT NULL DEFAULT '0',
  `dressUp_Clothe` int NOT NULL DEFAULT '0',
  `stylishKill_skin` int NOT NULL DEFAULT '0',
  `hongbo_point` int NOT NULL DEFAULT '0',
  `tsd_point` int NOT NULL DEFAULT '0',
  `ts_point` int NOT NULL DEFAULT '0',
  `tsd_total_point` int NOT NULL DEFAULT '0',
  `pet_loot` tinyint(1) NOT NULL DEFAULT '1',
  `dance_point` int NOT NULL DEFAULT '0',
  `total_dance_point` int NOT NULL DEFAULT '0',
  `boss_limit_clear_1` int NOT NULL DEFAULT '0',
  `boss_limit_clear_2` int NOT NULL DEFAULT '0',
  `boss_limit_clear_3` int NOT NULL DEFAULT '0',
  `draw_elf_ear` tinyint(1) DEFAULT '0',
  `draw_tail` tinyint(1) DEFAULT '0',
  `shift` tinyint(1) DEFAULT '0',
  `frozen_link_mobID` int DEFAULT '0',
  `frozen_link_mobCount` int DEFAULT '0',
  `enchant_point` int DEFAULT '0',
  `hu_failed_streak` int DEFAULT '0',
  `hu_last_failed_unique_id` bigint DEFAULT '0',
  `tteokguk_point` int DEFAULT '0',
  `second_base_color` int DEFAULT '-1',
  `second_add_color` int DEFAULT '0',
  `second_base_prob` int DEFAULT '0',
  `beta_state` tinyint(1) DEFAULT '0',
  `hyper_stat_index` int DEFAULT '0',
  `extra_1_option` int DEFAULT '-1',
  `extra_1_value` int DEFAULT '-1',
  `extra_2_option` int DEFAULT '-1',
  `extra_2_value` int DEFAULT '-1',
  `extra_3_option` int DEFAULT '-1',
  `extra_3_value` int DEFAULT '-1',
  `extra_4_option` int DEFAULT '-1',
  `extra_4_value` int DEFAULT '-1',
  `extra_5_option` int DEFAULT '-1',
  `extra_5_value` int DEFAULT '-1',
  `extra_6_option` int DEFAULT '-1',
  `extra_6_value` int DEFAULT '-1',
  `sub_extra_1_option` int DEFAULT '-1',
  `sub_extra_1_value` int DEFAULT '-1',
  `sub_extra_2_option` int DEFAULT '-1',
  `sub_extra_2_value` int DEFAULT '-1',
  `sub_extra_3_option` int DEFAULT '-1',
  `sub_extra_3_value` int DEFAULT '-1',
  `sub_extra_4_option` int DEFAULT '-1',
  `sub_extra_4_value` int DEFAULT '-1',
  `sub_extra_5_option` int DEFAULT '-1',
  `sub_extra_5_value` int DEFAULT '-1',
  `sub_extra_6_option` int DEFAULT '-1',
  `sub_extra_6_value` int DEFAULT '-1',
  `current_extra_slot` int DEFAULT '0',
  `current_extra_grade` int DEFAULT '0',
  `boss_tier` tinyint(1) DEFAULT '0',
  `skip_intro` tinyint(1) DEFAULT '0',
  `levelUpTime` timestamp NULL DEFAULT '1970-12-31 13:00:00',
  PRIMARY KEY (`id`),
  KEY `accountid` (`accountid`),
  KEY `id` (`id`),
  KEY `guildid` (`guildid`),
  KEY `name` (`name`),
  KEY `job` (`job`)
) ENGINE=InnoDB AUTO_INCREMENT=17424 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cheatlog`
--

DROP TABLE IF EXISTS `cheatlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cheatlog` (
  `id` int NOT NULL AUTO_INCREMENT,
  `characterid` int NOT NULL DEFAULT '0',
  `offense` tinytext NOT NULL,
  `count` int NOT NULL DEFAULT '0',
  `lastoffensetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `param` tinytext NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cid` (`characterid`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `check_time_schedule`
--

DROP TABLE IF EXISTS `check_time_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `check_time_schedule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `key` varchar(50) DEFAULT NULL,
  `value` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4265 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `compensationlog_confirmed`
--

DROP TABLE IF EXISTS `compensationlog_confirmed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compensationlog_confirmed` (
  `chrname` varchar(25) NOT NULL DEFAULT '',
  `donor` tinyint(1) NOT NULL DEFAULT '0',
  `value` int NOT NULL DEFAULT '0',
  `taken` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`chrname`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `consume_item_limit`
--

DROP TABLE IF EXISTS `consume_item_limit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consume_item_limit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_id` int NOT NULL DEFAULT '0',
  `item_id` int NOT NULL DEFAULT '0',
  `limit_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `player_id` (`player_id`),
  CONSTRAINT `FK__characters` FOREIGN KEY (`player_id`) REFERENCES `characters` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `id` int NOT NULL AUTO_INCREMENT,
  `couponid` varchar(50) NOT NULL DEFAULT '',
  `item` int NOT NULL DEFAULT '0',
  `qty` int NOT NULL DEFAULT '0',
  `starttime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `endtime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `maxcount` int NOT NULL DEFAULT '0',
  `currentcount` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `csequipenchant`
--

DROP TABLE IF EXISTS `csequipenchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `csequipenchant` (
  `inventoryequipenchantid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` bigint unsigned NOT NULL DEFAULT '0',
  `str` smallint NOT NULL DEFAULT '0',
  `dex` smallint NOT NULL DEFAULT '0',
  `int` smallint NOT NULL DEFAULT '0',
  `luk` smallint NOT NULL DEFAULT '0',
  `hp` smallint NOT NULL DEFAULT '0',
  `mp` smallint NOT NULL DEFAULT '0',
  `watk` smallint NOT NULL DEFAULT '0',
  `matk` smallint NOT NULL DEFAULT '0',
  `wdef` smallint NOT NULL DEFAULT '0',
  `mdef` smallint NOT NULL DEFAULT '0',
  `acc` smallint NOT NULL DEFAULT '0',
  `avoid` smallint NOT NULL DEFAULT '0',
  PRIMARY KEY (`inventoryequipenchantid`) USING BTREE,
  KEY `iv_idx` (`inventoryitemid`) USING BTREE,
  CONSTRAINT `FK_csequipenchant_copy_csequipment` FOREIGN KEY (`inventoryitemid`) REFERENCES `csequipment` (`inventoryitemid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `csequipment`
--

DROP TABLE IF EXISTS `csequipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `csequipment` (
  `inventoryequipmentid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` bigint unsigned NOT NULL DEFAULT '0',
  `player_id` int NOT NULL DEFAULT '0',
  `account_id` int NOT NULL DEFAULT '0',
  `item_id` int unsigned NOT NULL DEFAULT '0',
  `upgradeslots` int NOT NULL DEFAULT '0',
  `level` int NOT NULL DEFAULT '0',
  `str` int NOT NULL DEFAULT '0',
  `dex` int NOT NULL DEFAULT '0',
  `int` int NOT NULL DEFAULT '0',
  `luk` int NOT NULL DEFAULT '0',
  `arc` int NOT NULL DEFAULT '0',
  `arcexp` int NOT NULL DEFAULT '0',
  `arclevel` int NOT NULL DEFAULT '0',
  `hp` int NOT NULL DEFAULT '0',
  `mp` int NOT NULL DEFAULT '0',
  `hpR` smallint NOT NULL DEFAULT '0',
  `mpR` smallint NOT NULL DEFAULT '0',
  `watk` int NOT NULL DEFAULT '0',
  `matk` int NOT NULL DEFAULT '0',
  `wdef` int NOT NULL DEFAULT '0',
  `mdef` int NOT NULL DEFAULT '0',
  `acc` int NOT NULL DEFAULT '0',
  `avoid` int NOT NULL DEFAULT '0',
  `hands` int NOT NULL DEFAULT '0',
  `speed` int NOT NULL DEFAULT '0',
  `jump` int NOT NULL DEFAULT '0',
  `ViciousHammer` tinyint NOT NULL DEFAULT '0',
  `itemEXP` int NOT NULL DEFAULT '0',
  `durability` int NOT NULL DEFAULT '-1',
  `enhance` tinyint NOT NULL DEFAULT '0',
  `state` tinyint NOT NULL DEFAULT '0',
  `line` tinyint NOT NULL DEFAULT '0',
  `potential1` int NOT NULL DEFAULT '0',
  `potential2` int NOT NULL DEFAULT '0',
  `potential3` int NOT NULL DEFAULT '0',
  `potential4` int NOT NULL DEFAULT '0',
  `potential5` int NOT NULL DEFAULT '0',
  `potential6` int NOT NULL DEFAULT '0',
  `fusionAnvil` int NOT NULL DEFAULT '0',
  `incSkill` int NOT NULL DEFAULT '-1',
  `charmEXP` smallint NOT NULL DEFAULT '-1',
  `pvpDamage` smallint NOT NULL DEFAULT '0',
  `enhanctBuff` int NOT NULL DEFAULT '0',
  `reqLevel` int NOT NULL DEFAULT '0',
  `yggdrasilWisdom` tinyint NOT NULL DEFAULT '0',
  `finalStrike` tinyint NOT NULL DEFAULT '0',
  `bossDamage` int NOT NULL DEFAULT '0',
  `ignorePDR` int NOT NULL DEFAULT '0',
  `totalDamage` int NOT NULL DEFAULT '0',
  `allStat` int NOT NULL DEFAULT '0',
  `karmaCount` int NOT NULL DEFAULT '-1',
  `soulname` int NOT NULL DEFAULT '0',
  `soulenchanter` int NOT NULL DEFAULT '0',
  `soulpotential` int NOT NULL DEFAULT '0',
  `soulskill` int NOT NULL DEFAULT '0',
  `fire` bigint NOT NULL DEFAULT '-1',
  `starforce` int NOT NULL DEFAULT '0',
  `itemtrace` int NOT NULL DEFAULT '0',
  `downlevel` int NOT NULL DEFAULT '0',
  `additional_enhance` int NOT NULL DEFAULT '-1',
  `sp_grade` int NOT NULL DEFAULT '0',
  `sp_attack` int NOT NULL DEFAULT '0',
  `sp_all_stat` int NOT NULL DEFAULT '0',
  `item_state_flag` int NOT NULL DEFAULT '-1',
  `csoption_grade` int NOT NULL DEFAULT '0',
  `csoption1` int NOT NULL DEFAULT '0',
  `csoption2` int NOT NULL DEFAULT '0',
  `csoption3` int NOT NULL DEFAULT '0',
  `csoption_expired` bigint NOT NULL DEFAULT '-1',
  `ex_grade_option` bigint NOT NULL DEFAULT '0',
  `CHUC` int NOT NULL DEFAULT '0',
  `check_clear` int NOT NULL DEFAULT '0',
  `special_royal` int NOT NULL DEFAULT '0',
  `serial_number` bigint NOT NULL DEFAULT '0',
  `cash_enchant_count` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`inventoryequipmentid`),
  KEY `inventoryitemid` (`inventoryitemid`),
  CONSTRAINT `csequipment_ibfk_1` FOREIGN KEY (`inventoryitemid`) REFERENCES `csitems` (`inventoryitemid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `csitems`
--

DROP TABLE IF EXISTS `csitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `csitems` (
  `inventoryitemid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int DEFAULT NULL,
  `accountid` int DEFAULT NULL,
  `packageid` int DEFAULT NULL,
  `itemid` int NOT NULL DEFAULT '0',
  `inventorytype` int NOT NULL DEFAULT '0',
  `position` int NOT NULL DEFAULT '0',
  `quantity` int NOT NULL DEFAULT '0',
  `owner` tinytext,
  `GM_Log` tinytext,
  `uniqueid` bigint NOT NULL DEFAULT '-1',
  `flag` int NOT NULL DEFAULT '0',
  `expiredate` bigint NOT NULL DEFAULT '-1',
  `type` tinyint(1) NOT NULL DEFAULT '0',
  `sender` varchar(13) NOT NULL DEFAULT '',
  `once_trade` int DEFAULT '0',
  PRIMARY KEY (`inventoryitemid`),
  KEY `inventoryitems_ibfk_1` (`characterid`),
  KEY `characterid` (`characterid`),
  KEY `inventorytype` (`inventorytype`),
  KEY `accountid` (`accountid`),
  KEY `packageid` (`packageid`),
  KEY `characterid_2` (`characterid`,`inventorytype`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `custominfo`
--

DROP TABLE IF EXISTS `custominfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `custominfo` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int NOT NULL DEFAULT '0',
  `customkey` varchar(50) NOT NULL DEFAULT '0',
  `customData` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `characterid` (`characterid`) USING BTREE,
  KEY `quest` (`customkey`) USING BTREE,
  CONSTRAINT `custominfo_ibfk_1` FOREIGN KEY (`characterid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `custominfo_account`
--

DROP TABLE IF EXISTS `custominfo_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `custominfo_account` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `account_id` int NOT NULL DEFAULT '0',
  `customKey` varchar(30) NOT NULL DEFAULT '0',
  `customData` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `account_id` (`account_id`) USING BTREE,
  KEY `quest` (`customKey`) USING BTREE,
  CONSTRAINT `custominfo_account_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2924 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dailygift`
--

DROP TABLE IF EXISTS `dailygift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dailygift` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accountid` int DEFAULT '0',
  `daily_day` int DEFAULT '0',
  `daily_count` int DEFAULT '0',
  `daily_data` varchar(150) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `인덱스 2` (`accountid`),
  CONSTRAINT `FK__accounts` FOREIGN KEY (`accountid`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=873 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `damage_measurement_rank`
--

DROP TABLE IF EXISTS `damage_measurement_rank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `damage_measurement_rank` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_id` int DEFAULT '0',
  `damage` bigint DEFAULT '0',
  `name` varchar(13) CHARACTER SET euckr COLLATE euckr_korean_ci DEFAULT NULL,
  `job` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `KEY` (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1942 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `damage_skin_save`
--

DROP TABLE IF EXISTS `damage_skin_save`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `damage_skin_save` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_id` int DEFAULT NULL,
  `data` varchar(400) DEFAULT NULL,
  `slotCount` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `KEY` (`player_id`),
  CONSTRAINT `FK_damage_skin_save_characters` FOREIGN KEY (`player_id`) REFERENCES `characters` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=337 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dojang_ranking`
--

DROP TABLE IF EXISTS `dojang_ranking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dojang_ranking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` int DEFAULT '0',
  `rank` int DEFAULT '0',
  `job` int DEFAULT '0',
  `level` int DEFAULT '0',
  `point` int DEFAULT '0',
  `name` varchar(13) DEFAULT 'Extreme',
  `packed_avatar_look` blob,
  `week` int DEFAULT '1',
  `year` int DEFAULT '2020',
  PRIMARY KEY (`id`),
  KEY `account_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=65364 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dojang_ranking_calculate`
--

DROP TABLE IF EXISTS `dojang_ranking_calculate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dojang_ranking_calculate` (
  `last_update_week` int DEFAULT '0',
  `last_update_year` int DEFAULT '2020'
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `donation`
--

DROP TABLE IF EXISTS `donation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` text,
  `sum` int DEFAULT NULL,
  `comment` text,
  `date` text,
  `cid` int DEFAULT NULL,
  `check` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `donation_number`
--

DROP TABLE IF EXISTS `donation_number`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation_number` (
  `id` int NOT NULL AUTO_INCREMENT,
  `number` varchar(50) CHARACTER SET euckr COLLATE euckr_korean_ci DEFAULT NULL,
  `accountid` int NOT NULL,
  `count` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `accountid` (`accountid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `donation_request`
--

DROP TABLE IF EXISTS `donation_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation_request` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_name` varchar(50) DEFAULT '',
  `player_name` varchar(50) DEFAULT '',
  `real_name` varchar(50) DEFAULT '',
  `point` int DEFAULT '0',
  `type` int DEFAULT '0',
  `status` int DEFAULT '0',
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `bot` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `donorlog`
--

DROP TABLE IF EXISTS `donorlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donorlog` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accname` varchar(25) NOT NULL DEFAULT '',
  `accId` int NOT NULL DEFAULT '0',
  `chrname` varchar(25) NOT NULL DEFAULT '',
  `chrId` int NOT NULL DEFAULT '0',
  `log` varchar(4096) NOT NULL DEFAULT '',
  `time` varchar(25) NOT NULL DEFAULT '',
  `previousPoints` int NOT NULL DEFAULT '0',
  `currentPoints` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dream_breaker_rank`
--

DROP TABLE IF EXISTS `dream_breaker_rank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dream_breaker_rank` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(13) DEFAULT '',
  `best_stage` int DEFAULT '0',
  `best_time` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dueyequipenchant`
--

DROP TABLE IF EXISTS `dueyequipenchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dueyequipenchant` (
  `inventoryequipenchantid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` bigint unsigned NOT NULL DEFAULT '0',
  `str` smallint NOT NULL DEFAULT '0',
  `dex` smallint NOT NULL DEFAULT '0',
  `int` smallint NOT NULL DEFAULT '0',
  `luk` smallint NOT NULL DEFAULT '0',
  `hp` smallint NOT NULL DEFAULT '0',
  `mp` smallint NOT NULL DEFAULT '0',
  `watk` smallint NOT NULL DEFAULT '0',
  `matk` smallint NOT NULL DEFAULT '0',
  `wdef` smallint NOT NULL DEFAULT '0',
  `mdef` smallint NOT NULL DEFAULT '0',
  `acc` smallint NOT NULL DEFAULT '0',
  `avoid` smallint NOT NULL DEFAULT '0',
  PRIMARY KEY (`inventoryequipenchantid`) USING BTREE,
  KEY `iv_idx` (`inventoryitemid`) USING BTREE,
  CONSTRAINT `FK_dueyequipenchant_copy_dueyequipment` FOREIGN KEY (`inventoryitemid`) REFERENCES `dueyequipment` (`inventoryitemid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dueyequipment`
--

DROP TABLE IF EXISTS `dueyequipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dueyequipment` (
  `inventoryequipmentid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` bigint unsigned NOT NULL DEFAULT '0',
  `player_id` int NOT NULL DEFAULT '0',
  `account_id` int NOT NULL DEFAULT '0',
  `item_id` int unsigned NOT NULL DEFAULT '0',
  `upgradeslots` int NOT NULL DEFAULT '0',
  `level` int NOT NULL DEFAULT '0',
  `str` int NOT NULL DEFAULT '0',
  `dex` int NOT NULL DEFAULT '0',
  `int` int NOT NULL DEFAULT '0',
  `luk` int NOT NULL DEFAULT '0',
  `hp` int NOT NULL DEFAULT '0',
  `mp` int NOT NULL DEFAULT '0',
  `hpR` smallint NOT NULL DEFAULT '0',
  `mpR` smallint NOT NULL DEFAULT '0',
  `watk` int NOT NULL DEFAULT '0',
  `matk` int NOT NULL DEFAULT '0',
  `wdef` int NOT NULL DEFAULT '0',
  `mdef` int NOT NULL DEFAULT '0',
  `acc` int NOT NULL DEFAULT '0',
  `avoid` int NOT NULL DEFAULT '0',
  `hands` int NOT NULL DEFAULT '0',
  `speed` int NOT NULL DEFAULT '0',
  `jump` int NOT NULL DEFAULT '0',
  `ViciousHammer` tinyint NOT NULL DEFAULT '0',
  `itemEXP` int NOT NULL DEFAULT '0',
  `durability` int NOT NULL DEFAULT '-1',
  `enhance` tinyint NOT NULL DEFAULT '0',
  `state` tinyint NOT NULL DEFAULT '0',
  `line` tinyint NOT NULL DEFAULT '0',
  `potential1` int NOT NULL DEFAULT '0',
  `potential2` int NOT NULL DEFAULT '0',
  `potential3` int NOT NULL DEFAULT '0',
  `potential4` int NOT NULL DEFAULT '0',
  `potential5` int NOT NULL DEFAULT '0',
  `potential6` int NOT NULL DEFAULT '0',
  `fusionAnvil` int NOT NULL DEFAULT '0',
  `incSkill` int NOT NULL DEFAULT '-1',
  `charmEXP` smallint NOT NULL DEFAULT '-1',
  `pvpDamage` smallint NOT NULL DEFAULT '0',
  `enhanctBuff` int NOT NULL DEFAULT '0',
  `reqLevel` int NOT NULL DEFAULT '0',
  `yggdrasilWisdom` tinyint NOT NULL DEFAULT '0',
  `finalStrike` tinyint NOT NULL DEFAULT '0',
  `bossDamage` int NOT NULL DEFAULT '0',
  `ignorePDR` int NOT NULL DEFAULT '0',
  `totalDamage` int NOT NULL DEFAULT '0',
  `allStat` int NOT NULL DEFAULT '0',
  `karmaCount` int NOT NULL DEFAULT '-1',
  `soulname` int NOT NULL DEFAULT '0',
  `soulenchanter` int NOT NULL DEFAULT '0',
  `soulpotential` int NOT NULL DEFAULT '0',
  `soulskill` int NOT NULL DEFAULT '0',
  `fire` int NOT NULL DEFAULT '-1',
  `starforce` int NOT NULL DEFAULT '0',
  `downlevel` int NOT NULL DEFAULT '0',
  `arc` int NOT NULL DEFAULT '0',
  `arcexp` int NOT NULL DEFAULT '0',
  `arclevel` int NOT NULL DEFAULT '0',
  `additional_enhance` int NOT NULL DEFAULT '-1',
  `sp_grade` int NOT NULL DEFAULT '0',
  `sp_attack` int NOT NULL DEFAULT '0',
  `sp_all_stat` int NOT NULL DEFAULT '0',
  `item_state_flag` int NOT NULL DEFAULT '-1',
  `csoption_grade` int NOT NULL DEFAULT '0',
  `csoption1` int NOT NULL DEFAULT '0',
  `csoption2` int NOT NULL DEFAULT '0',
  `csoption3` int NOT NULL DEFAULT '0',
  `csoption_expired` bigint NOT NULL DEFAULT '-1',
  `ex_grade_option` bigint NOT NULL DEFAULT '0',
  `CHUC` int NOT NULL DEFAULT '0',
  `check_clear` int NOT NULL DEFAULT '0',
  `special_royal` int NOT NULL DEFAULT '0',
  `serial_number` bigint NOT NULL DEFAULT '0',
  `cash_enchant_count` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`inventoryequipmentid`),
  KEY `inventoryitemid` (`inventoryitemid`),
  CONSTRAINT `dueyequipment_ibfk_1` FOREIGN KEY (`inventoryitemid`) REFERENCES `dueyitems` (`inventoryitemid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dueyitems`
--

DROP TABLE IF EXISTS `dueyitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dueyitems` (
  `inventoryitemid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int DEFAULT NULL,
  `accountid` int DEFAULT NULL,
  `packageid` int DEFAULT NULL,
  `itemid` int NOT NULL DEFAULT '0',
  `inventorytype` int NOT NULL DEFAULT '0',
  `position` int NOT NULL DEFAULT '0',
  `quantity` int NOT NULL DEFAULT '0',
  `owner` tinytext,
  `GM_Log` tinytext,
  `uniqueid` bigint NOT NULL DEFAULT '-1',
  `flag` int NOT NULL DEFAULT '0',
  `expiredate` bigint NOT NULL DEFAULT '-1',
  `type` tinyint(1) NOT NULL DEFAULT '0',
  `sender` varchar(13) NOT NULL DEFAULT '',
  `once_trade` int DEFAULT '0',
  PRIMARY KEY (`inventoryitemid`),
  KEY `inventoryitems_ibfk_1` (`characterid`),
  KEY `characterid` (`characterid`),
  KEY `inventorytype` (`inventorytype`),
  KEY `accountid` (`accountid`),
  KEY `packageid` (`packageid`),
  KEY `characterid_2` (`characterid`,`inventorytype`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dueypackages`
--

DROP TABLE IF EXISTS `dueypackages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dueypackages` (
  `PackageId` int unsigned NOT NULL AUTO_INCREMENT,
  `RecieverId` int NOT NULL,
  `SenderName` varchar(13) NOT NULL,
  `Mesos` int unsigned DEFAULT '0',
  `content` varchar(200) NOT NULL DEFAULT '',
  `Quick` tinyint(1) NOT NULL,
  `TimeStamp` bigint unsigned DEFAULT NULL,
  `Checked` tinyint unsigned DEFAULT '1',
  `Type` tinyint unsigned NOT NULL,
  PRIMARY KEY (`PackageId`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `extendedslots`
--

DROP TABLE IF EXISTS `extendedslots`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `extendedslots` (
  `id` int NOT NULL AUTO_INCREMENT,
  `characterid` int NOT NULL DEFAULT '0',
  `itemId` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=413 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `famelog`
--

DROP TABLE IF EXISTS `famelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `famelog` (
  `famelogid` int NOT NULL AUTO_INCREMENT,
  `characterid` int NOT NULL DEFAULT '0',
  `characterid_to` int NOT NULL DEFAULT '0',
  `when` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`famelogid`),
  KEY `characterid` (`characterid`),
  CONSTRAINT `famelog_ibfk_1` FOREIGN KEY (`characterid`) REFERENCES `characters` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=212 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `families`
--

DROP TABLE IF EXISTS `families`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `families` (
  `familyid` int NOT NULL AUTO_INCREMENT,
  `leaderid` int NOT NULL DEFAULT '0',
  `notice` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`familyid`),
  KEY `familyid` (`familyid`),
  KEY `leaderid` (`leaderid`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fireitem`
--

DROP TABLE IF EXISTS `fireitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fireitem` (
  `value` int unsigned NOT NULL AUTO_INCREMENT,
  `uniqueid` bigint NOT NULL DEFAULT '0',
  `watk` int NOT NULL DEFAULT '0',
  `matk` int NOT NULL DEFAULT '0',
  `wdef` int NOT NULL DEFAULT '0',
  `mdef` int NOT NULL DEFAULT '0',
  `hp` int NOT NULL DEFAULT '0',
  `mp` int NOT NULL DEFAULT '0',
  `str` int NOT NULL DEFAULT '0',
  `dex` int NOT NULL DEFAULT '0',
  `_int` int NOT NULL DEFAULT '0',
  `luk` int NOT NULL DEFAULT '0',
  `acc` int NOT NULL DEFAULT '0',
  `avoid` int NOT NULL DEFAULT '0',
  `jump` int NOT NULL DEFAULT '0',
  `hands` int NOT NULL DEFAULT '0',
  `speed` int NOT NULL DEFAULT '0',
  `bossdamage` int NOT NULL DEFAULT '0',
  `ignorepdr` int NOT NULL DEFAULT '0',
  `allstat` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `futurehope`
--

DROP TABLE IF EXISTS `futurehope`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `futurehope` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `cid` int unsigned NOT NULL,
  `hope` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gifts`
--

DROP TABLE IF EXISTS `gifts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gifts` (
  `giftid` int unsigned NOT NULL AUTO_INCREMENT,
  `recipient` int NOT NULL DEFAULT '0',
  `from` varchar(13) NOT NULL DEFAULT '',
  `message` varchar(255) NOT NULL DEFAULT '',
  `sn` int NOT NULL DEFAULT '0',
  `uniqueid` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`giftid`),
  KEY `recipient` (`recipient`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gmlog`
--

DROP TABLE IF EXISTS `gmlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gmlog` (
  `gmlogid` int NOT NULL AUTO_INCREMENT,
  `cid` int NOT NULL DEFAULT '0',
  `command` text NOT NULL,
  `mapid` int NOT NULL DEFAULT '0',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`gmlogid`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `guildcontents`
--

DROP TABLE IF EXISTS `guildcontents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guildcontents` (
  `id` int NOT NULL AUTO_INCREMENT,
  `guildid` int NOT NULL,
  `characterid` int NOT NULL,
  `type` int NOT NULL,
  `lastweekpoint` int NOT NULL,
  `lastweektime` bigint NOT NULL,
  `thisweekpoint` int NOT NULL,
  `thisweektime` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67348 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `guilds`
--

DROP TABLE IF EXISTS `guilds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guilds` (
  `guildid` int unsigned NOT NULL AUTO_INCREMENT,
  `leader` int unsigned NOT NULL DEFAULT '0',
  `GP` int NOT NULL DEFAULT '0',
  `logo` int unsigned DEFAULT NULL,
  `logoColor` smallint unsigned NOT NULL DEFAULT '0',
  `name` varchar(45) NOT NULL,
  `rank1title` varchar(45) NOT NULL DEFAULT '마스터',
  `rank1permission` int NOT NULL DEFAULT '-1',
  `rank2title` varchar(45) NOT NULL DEFAULT '부마스터',
  `rank2permission` int NOT NULL DEFAULT '1151',
  `rank3title` varchar(45) NOT NULL DEFAULT '길드원',
  `rank3permission` int NOT NULL DEFAULT '1024',
  `rank4title` varchar(45) NOT NULL DEFAULT '길드원',
  `rank4permission` int NOT NULL DEFAULT '1024',
  `rank5title` varchar(45) NOT NULL DEFAULT '길드원',
  `rank5permission` int NOT NULL DEFAULT '1024',
  `rank6title` varchar(50) NOT NULL DEFAULT '길드원',
  `rank6permission` int NOT NULL DEFAULT '1024',
  `rank7title` varchar(50) NOT NULL DEFAULT '길드원',
  `rank7permission` int NOT NULL DEFAULT '1024',
  `rank8title` varchar(50) NOT NULL DEFAULT '길드원',
  `rank8permission` int NOT NULL DEFAULT '1024',
  `rank9title` varchar(50) NOT NULL DEFAULT '길드원',
  `rank9permission` int NOT NULL DEFAULT '1024',
  `rank10title` varchar(50) NOT NULL DEFAULT '길드원',
  `rank10permission` int NOT NULL DEFAULT '1024',
  `capacity` int unsigned NOT NULL DEFAULT '10',
  `logoBG` int unsigned DEFAULT NULL,
  `logoBGColor` smallint unsigned NOT NULL DEFAULT '0',
  `notice` varchar(150) DEFAULT NULL,
  `signature` int NOT NULL DEFAULT '0',
  `alliance` int unsigned NOT NULL DEFAULT '0',
  `connectTimeFlag` int unsigned NOT NULL DEFAULT '0',
  `activityFlag` int unsigned NOT NULL DEFAULT '0',
  `ageGroupFlag` int unsigned NOT NULL DEFAULT '0',
  `allowJoinRequest` int unsigned NOT NULL DEFAULT '1',
  `honorEXP` int NOT NULL DEFAULT '0',
  `customEmblem` blob,
  `noblessSkillPoint` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`guildid`),
  UNIQUE KEY `name` (`name`),
  KEY `guildid` (`guildid`),
  KEY `leader` (`leader`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `guildskills`
--

DROP TABLE IF EXISTS `guildskills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guildskills` (
  `id` int NOT NULL AUTO_INCREMENT,
  `guildid` int NOT NULL DEFAULT '0',
  `skillid` int NOT NULL DEFAULT '0',
  `level` smallint NOT NULL DEFAULT '1',
  `timestamp` bigint NOT NULL DEFAULT '0',
  `purchaser` varchar(13) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1030 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hexa_cores`
--

DROP TABLE IF EXISTS `hexa_cores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hexa_cores` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `player_id` int NOT NULL DEFAULT '0',
  `coreid` int NOT NULL DEFAULT '0',
  `level` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `player_id` (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=30991 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hexa_stats`
--

DROP TABLE IF EXISTS `hexa_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hexa_stats` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `player_id` int NOT NULL DEFAULT '0',
  `index` tinyint NOT NULL DEFAULT '0',
  `pos` tinyint NOT NULL DEFAULT '0',
  `type` tinyint NOT NULL DEFAULT '0',
  `level` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `chrid` (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1435 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hongbo`
--

DROP TABLE IF EXISTS `hongbo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hongbo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` text,
  `check` int DEFAULT '0',
  `youtube` int DEFAULT NULL,
  `blog` int DEFAULT NULL,
  `etc` int DEFAULT NULL,
  `comment` text,
  `date` text,
  `cid` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=626 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hyper_stats`
--

DROP TABLE IF EXISTS `hyper_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hyper_stats` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `player_id` int NOT NULL,
  `index` int NOT NULL,
  `skill_id` int DEFAULT NULL,
  `skill_level` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_idx` (`player_id`),
  CONSTRAINT `id` FOREIGN KEY (`player_id`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=829214 DEFAULT CHARSET=euckr COLLATE=euckr_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hyperrocklocations`
--

DROP TABLE IF EXISTS `hyperrocklocations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hyperrocklocations` (
  `trockid` int NOT NULL AUTO_INCREMENT,
  `characterid` int DEFAULT NULL,
  `mapid` int DEFAULT NULL,
  PRIMARY KEY (`trockid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `imps`
--

DROP TABLE IF EXISTS `imps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `imps` (
  `impid` int unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int NOT NULL DEFAULT '0',
  `itemid` int NOT NULL DEFAULT '0',
  `level` tinyint unsigned NOT NULL DEFAULT '1',
  `state` tinyint unsigned NOT NULL DEFAULT '1',
  `closeness` mediumint unsigned NOT NULL DEFAULT '0',
  `fullness` mediumint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`impid`),
  KEY `impid` (`impid`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inner_ability_skills`
--

DROP TABLE IF EXISTS `inner_ability_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inner_ability_skills` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_id` int NOT NULL,
  `skill_id` int NOT NULL,
  `skill_level` int NOT NULL,
  `max_level` int NOT NULL,
  `rank` int NOT NULL,
  `locked` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `player_id` (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=108565 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `intense_power_crystal`
--

DROP TABLE IF EXISTS `intense_power_crystal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `intense_power_crystal` (
  `id` int NOT NULL AUTO_INCREMENT,
  `item_unique_id` bigint DEFAULT '0',
  `player_id` int DEFAULT '0',
  `mob_id` int DEFAULT '0',
  `member_count` int DEFAULT '0',
  `price` bigint DEFAULT NULL,
  `unk` bigint DEFAULT '0',
  `gain_time` bigint DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `KEY` (`item_unique_id`),
  KEY `player_id` (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=380055 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `internlog`
--

DROP TABLE IF EXISTS `internlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `internlog` (
  `gmlogid` int NOT NULL AUTO_INCREMENT,
  `cid` int NOT NULL DEFAULT '0',
  `command` tinytext NOT NULL,
  `mapid` int NOT NULL DEFAULT '0',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`gmlogid`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inventoryequipenchant`
--

DROP TABLE IF EXISTS `inventoryequipenchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventoryequipenchant` (
  `inventoryequipenchantid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` bigint unsigned NOT NULL DEFAULT '0',
  `str` smallint NOT NULL DEFAULT '0',
  `dex` smallint NOT NULL DEFAULT '0',
  `int` smallint NOT NULL DEFAULT '0',
  `luk` smallint NOT NULL DEFAULT '0',
  `hp` smallint NOT NULL DEFAULT '0',
  `mp` smallint NOT NULL DEFAULT '0',
  `watk` smallint NOT NULL DEFAULT '0',
  `matk` smallint NOT NULL DEFAULT '0',
  `wdef` smallint NOT NULL DEFAULT '0',
  `mdef` smallint NOT NULL DEFAULT '0',
  `acc` smallint NOT NULL DEFAULT '0',
  `avoid` smallint NOT NULL DEFAULT '0',
  PRIMARY KEY (`inventoryequipenchantid`),
  KEY `iv_idx` (`inventoryitemid`),
  CONSTRAINT `iv` FOREIGN KEY (`inventoryitemid`) REFERENCES `inventoryequipment` (`inventoryitemid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inventoryequipexceptional`
--

DROP TABLE IF EXISTS `inventoryequipexceptional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventoryequipexceptional` (
  `inventoryequipexceptid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` bigint unsigned NOT NULL DEFAULT '0',
  `slot` smallint unsigned NOT NULL DEFAULT '0',
  `str` smallint NOT NULL DEFAULT '0',
  `dex` smallint NOT NULL DEFAULT '0',
  `int` smallint NOT NULL DEFAULT '0',
  `luk` smallint NOT NULL DEFAULT '0',
  `hp` smallint NOT NULL DEFAULT '0',
  `mp` smallint NOT NULL DEFAULT '0',
  `watk` smallint NOT NULL DEFAULT '0',
  `matk` smallint NOT NULL DEFAULT '0',
  `wdef` smallint NOT NULL DEFAULT '0',
  `mdef` smallint NOT NULL DEFAULT '0',
  `acc` smallint NOT NULL DEFAULT '0',
  `avoid` smallint NOT NULL DEFAULT '0',
  PRIMARY KEY (`inventoryequipexceptid`) USING BTREE,
  KEY `iv2_idx` (`inventoryitemid`) USING BTREE,
  CONSTRAINT `vi2` FOREIGN KEY (`inventoryitemid`) REFERENCES `inventoryequipment` (`inventoryitemid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=327 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inventoryequipment`
--

DROP TABLE IF EXISTS `inventoryequipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventoryequipment` (
  `inventoryequipmentid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` bigint unsigned NOT NULL DEFAULT '0',
  `player_id` int NOT NULL DEFAULT '0',
  `account_id` int NOT NULL DEFAULT '0',
  `item_id` int unsigned NOT NULL DEFAULT '0',
  `upgradeslots` tinyint unsigned NOT NULL DEFAULT '0',
  `level` tinyint unsigned NOT NULL DEFAULT '0',
  `str` smallint NOT NULL DEFAULT '0',
  `dex` smallint NOT NULL DEFAULT '0',
  `int` smallint NOT NULL DEFAULT '0',
  `luk` smallint NOT NULL DEFAULT '0',
  `arc` int NOT NULL DEFAULT '0',
  `arcexp` int NOT NULL DEFAULT '0',
  `arclevel` int NOT NULL DEFAULT '0',
  `hp` smallint NOT NULL DEFAULT '0',
  `mp` smallint NOT NULL DEFAULT '0',
  `hpR` smallint NOT NULL DEFAULT '0',
  `mpR` smallint NOT NULL DEFAULT '0',
  `watk` smallint NOT NULL DEFAULT '0',
  `matk` smallint NOT NULL DEFAULT '0',
  `wdef` smallint NOT NULL DEFAULT '0',
  `mdef` smallint NOT NULL DEFAULT '0',
  `acc` smallint NOT NULL DEFAULT '0',
  `avoid` smallint NOT NULL DEFAULT '0',
  `hands` smallint NOT NULL DEFAULT '0',
  `speed` smallint NOT NULL DEFAULT '0',
  `jump` smallint NOT NULL DEFAULT '0',
  `ViciousHammer` tinyint NOT NULL DEFAULT '0',
  `itemEXP` int NOT NULL DEFAULT '0',
  `durability` mediumint NOT NULL DEFAULT '-1',
  `enhance` tinyint NOT NULL DEFAULT '0',
  `state` tinyint NOT NULL DEFAULT '0',
  `line` tinyint NOT NULL DEFAULT '0',
  `potential1` int NOT NULL DEFAULT '0',
  `potential2` int NOT NULL DEFAULT '0',
  `potential3` int NOT NULL DEFAULT '0',
  `potential4` int NOT NULL DEFAULT '0',
  `potential5` int NOT NULL DEFAULT '0',
  `potential6` int NOT NULL DEFAULT '0',
  `fusionAnvil` int NOT NULL DEFAULT '0',
  `incSkill` int NOT NULL DEFAULT '-1',
  `charmEXP` smallint NOT NULL DEFAULT '-1',
  `pvpDamage` smallint NOT NULL DEFAULT '0',
  `enhanctBuff` int NOT NULL DEFAULT '0',
  `reqLevel` int NOT NULL DEFAULT '0',
  `yggdrasilWisdom` tinyint NOT NULL DEFAULT '0',
  `finalStrike` tinyint NOT NULL DEFAULT '0',
  `bossDamage` int NOT NULL DEFAULT '0',
  `ignorePDR` int NOT NULL DEFAULT '0',
  `totalDamage` int NOT NULL DEFAULT '0',
  `allStat` int NOT NULL DEFAULT '0',
  `karmaCount` int NOT NULL DEFAULT '-1',
  `soulname` int NOT NULL DEFAULT '0',
  `soulenchanter` int NOT NULL DEFAULT '0',
  `soulpotential` int NOT NULL DEFAULT '0',
  `soulskill` int NOT NULL DEFAULT '0',
  `fire` bigint NOT NULL DEFAULT '-1',
  `starforce` int NOT NULL DEFAULT '0',
  `itemtrace` int NOT NULL DEFAULT '0',
  `downlevel` int NOT NULL DEFAULT '0',
  `additional_enhance` int NOT NULL DEFAULT '-1',
  `sp_grade` int NOT NULL DEFAULT '0',
  `sp_attack` int NOT NULL DEFAULT '0',
  `sp_all_stat` int NOT NULL DEFAULT '0',
  `item_state_flag` int NOT NULL DEFAULT '-1',
  `csoption_grade` int NOT NULL DEFAULT '0',
  `csoption1` int NOT NULL DEFAULT '0',
  `csoption2` int NOT NULL DEFAULT '0',
  `csoption3` int NOT NULL DEFAULT '0',
  `csoption_expired` bigint NOT NULL DEFAULT '-1',
  `ex_grade_option` bigint NOT NULL DEFAULT '0',
  `CHUC` int NOT NULL DEFAULT '0',
  `check_clear` int NOT NULL DEFAULT '0',
  `special_royal` int NOT NULL DEFAULT '0',
  `serial_number` bigint NOT NULL DEFAULT '0',
  `cash_enchant_count` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`inventoryequipmentid`),
  KEY `inventoryitemid` (`inventoryitemid`),
  KEY `itemid` (`item_id`),
  KEY `playerid` (`player_id`),
  KEY `accountid` (`account_id`),
  CONSTRAINT `inventoryequipment_ibfk_1` FOREIGN KEY (`inventoryitemid`) REFERENCES `inventoryitems` (`inventoryitemid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28559227 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inventoryitems`
--

DROP TABLE IF EXISTS `inventoryitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventoryitems` (
  `inventoryitemid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int DEFAULT NULL,
  `accountid` int DEFAULT NULL,
  `packageid` int DEFAULT NULL,
  `itemid` int NOT NULL DEFAULT '0',
  `inventorytype` int NOT NULL DEFAULT '0',
  `position` int NOT NULL DEFAULT '0',
  `quantity` int NOT NULL DEFAULT '0',
  `owner` varchar(10) DEFAULT NULL,
  `GM_Log` varchar(200) DEFAULT NULL,
  `uniqueid` bigint NOT NULL DEFAULT '-1',
  `flag` int NOT NULL DEFAULT '0',
  `expiredate` bigint NOT NULL DEFAULT '-1',
  `type` tinyint(1) NOT NULL DEFAULT '0',
  `sender` varchar(10) NOT NULL DEFAULT '',
  `once_trade` int DEFAULT '0',
  PRIMARY KEY (`inventoryitemid`),
  KEY `inventorytype` (`inventorytype`),
  KEY `accountid` (`accountid`),
  KEY `itemid` (`itemid`) USING BTREE,
  KEY `characterid` (`characterid`) USING BTREE,
  KEY `uniqueid` (`uniqueid`)
) ENGINE=InnoDB AUTO_INCREMENT=77118912 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inventorylog`
--

DROP TABLE IF EXISTS `inventorylog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventorylog` (
  `inventorylogid` int unsigned NOT NULL AUTO_INCREMENT,
  `inventoryitemid` int unsigned NOT NULL DEFAULT '0',
  `msg` tinytext NOT NULL,
  PRIMARY KEY (`inventorylogid`),
  KEY `inventoryitemid` (`inventoryitemid`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inventoryslot`
--

DROP TABLE IF EXISTS `inventoryslot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventoryslot` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int unsigned DEFAULT NULL,
  `equip` int unsigned DEFAULT NULL,
  `use` int unsigned DEFAULT NULL,
  `setup` int unsigned DEFAULT NULL,
  `etc` int unsigned DEFAULT NULL,
  `cash` int DEFAULT NULL,
  `cash_equip` int DEFAULT '128',
  PRIMARY KEY (`id`),
  UNIQUE KEY `characterid` (`characterid`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=401447 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ipbans`
--

DROP TABLE IF EXISTS `ipbans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ipbans` (
  `ipbanid` int unsigned NOT NULL AUTO_INCREMENT,
  `ip` varchar(40) NOT NULL DEFAULT '',
  `memo` varchar(50) DEFAULT '',
  PRIMARY KEY (`ipbanid`),
  UNIQUE KEY `ip` (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `iplog`
--

DROP TABLE IF EXISTS `iplog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `iplog` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `accid` int NOT NULL,
  `ip` varchar(45) NOT NULL,
  `time` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ipvotelog`
--

DROP TABLE IF EXISTS `ipvotelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ipvotelog` (
  `vid` int unsigned NOT NULL AUTO_INCREMENT,
  `accid` varchar(45) NOT NULL DEFAULT '0',
  `ipaddress` varchar(30) NOT NULL DEFAULT '127.0.0.1',
  `votetime` varchar(100) NOT NULL DEFAULT '0',
  `votetype` tinyint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `keymap`
--

DROP TABLE IF EXISTS `keymap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `keymap` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int NOT NULL DEFAULT '0',
  `key` tinyint unsigned NOT NULL DEFAULT '0',
  `type` tinyint unsigned NOT NULL DEFAULT '0',
  `action` int NOT NULL DEFAULT '0',
  `index` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `characterid` (`characterid`)
) ENGINE=InnoDB AUTO_INCREMENT=12225536 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `keyvalue`
--

DROP TABLE IF EXISTS `keyvalue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `keyvalue` (
  `id` int DEFAULT NULL,
  `key` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  KEY `Index_1` (`id`) USING BTREE,
  KEY `key` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `linkskill_preset`
--

DROP TABLE IF EXISTS `linkskill_preset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `linkskill_preset` (
  `id` int NOT NULL AUTO_INCREMENT,
  `character_id` int NOT NULL DEFAULT '0',
  `skill_id` int NOT NULL DEFAULT '0',
  `linking_cid` int NOT NULL DEFAULT '0',
  `preset` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `character_id` (`character_id`)
) ENGINE=InnoDB AUTO_INCREMENT=82588 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `macbans`
--

DROP TABLE IF EXISTS `macbans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `macbans` (
  `macbanid` int unsigned NOT NULL AUTO_INCREMENT,
  `mac` varchar(30) NOT NULL,
  `memo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`macbanid`),
  UNIQUE KEY `mac_2` (`mac`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `macfilters`
--

DROP TABLE IF EXISTS `macfilters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `macfilters` (
  `macfilterid` int unsigned NOT NULL AUTO_INCREMENT,
  `filter` varchar(30) NOT NULL,
  PRIMARY KEY (`macfilterid`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mannequin`
--

DROP TABLE IF EXISTS `mannequin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mannequin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_id` int DEFAULT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `item_id` int DEFAULT NULL,
  `base_color` tinyint DEFAULT NULL,
  `add_color` tinyint DEFAULT NULL,
  `base_prob` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `KEY` (`account_id`,`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7164 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mannequin_slotmax`
--

DROP TABLE IF EXISTS `mannequin_slotmax`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mannequin_slotmax` (
  `account_id` int NOT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `slot_max` tinyint DEFAULT NULL,
  PRIMARY KEY (`account_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `maple_union_data`
--

DROP TABLE IF EXISTS `maple_union_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maple_union_data` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_id` int DEFAULT '0',
  `rank` int DEFAULT '0',
  `current_preset` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `KEY` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7845 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `maple_union_group`
--

DROP TABLE IF EXISTS `maple_union_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maple_union_group` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_id` int DEFAULT '0',
  `preset` int DEFAULT '0',
  `changeable_group` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `KEY` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39221 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `maple_union_raiders`
--

DROP TABLE IF EXISTS `maple_union_raiders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maple_union_raiders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_id` int DEFAULT '0',
  `type` int DEFAULT '0',
  `preset` int DEFAULT '0',
  `player_id` int DEFAULT '0',
  `angle` int DEFAULT '0',
  `board` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `KEY` (`player_id`,`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=255387 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `memo`
--

DROP TABLE IF EXISTS `memo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `memo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `title` text NOT NULL,
  `date` varchar(64) DEFAULT NULL,
  `memo` text NOT NULL,
  `reply` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mob_quest`
--

DROP TABLE IF EXISTS `mob_quest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mob_quest` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_id` int DEFAULT '0',
  `quest_id` int DEFAULT '0',
  `mob_id` int DEFAULT '0',
  `need_count` int DEFAULT '0',
  `mob_count` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `KEY` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `mountdata`
--

DROP TABLE IF EXISTS `mountdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mountdata` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int unsigned DEFAULT NULL,
  `Level` int unsigned NOT NULL DEFAULT '0',
  `Exp` int unsigned NOT NULL DEFAULT '0',
  `Fatigue` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `characterid` (`characterid`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17424 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notes`
--

DROP TABLE IF EXISTS `notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` tinyint NOT NULL DEFAULT '0',
  `fromcid` int NOT NULL DEFAULT '0',
  `from` varchar(13) NOT NULL DEFAULT '',
  `tocid` int NOT NULL DEFAULT '0',
  `to` varchar(13) NOT NULL DEFAULT '',
  `message` text NOT NULL,
  `timestamp` bigint unsigned NOT NULL DEFAULT '0',
  `gift` tinyint(1) NOT NULL DEFAULT '0',
  `checked` tinyint(1) NOT NULL DEFAULT '0',
  `realtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `KEY` (`to`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `nxcode`
--

DROP TABLE IF EXISTS `nxcode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nxcode` (
  `code` varchar(15) NOT NULL,
  `valid` int NOT NULL DEFAULT '1',
  `user` varchar(13) DEFAULT NULL,
  `type` int NOT NULL DEFAULT '0',
  `item` int NOT NULL DEFAULT '10000',
  PRIMARY KEY (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `offline`
--

DROP TABLE IF EXISTS `offline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offline` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `accid` int NOT NULL DEFAULT '0',
  `nickname` varchar(13) NOT NULL DEFAULT '',
  `chrid` int NOT NULL,
  `itemname` varchar(50) NOT NULL DEFAULT '',
  `item` int NOT NULL,
  `qua` int NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `chrid` (`chrid`),
  KEY `accid` (`accid`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `offlinelog`
--

DROP TABLE IF EXISTS `offlinelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offlinelog` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `accid` int NOT NULL DEFAULT '0',
  `nickname` varchar(13) NOT NULL DEFAULT '',
  `chrid` int NOT NULL,
  `itemname` varchar(50) NOT NULL DEFAULT '',
  `item` int NOT NULL,
  `qua` int NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pets`
--

DROP TABLE IF EXISTS `pets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pets` (
  `petid` bigint unsigned NOT NULL,
  `name` varchar(13) DEFAULT NULL,
  `level` int unsigned NOT NULL,
  `closeness` int unsigned NOT NULL,
  `fullness` int unsigned NOT NULL,
  `seconds` int NOT NULL DEFAULT '0',
  `flags` smallint NOT NULL DEFAULT '0',
  `petbuff` int NOT NULL DEFAULT '0',
  `petbuff2` int NOT NULL DEFAULT '0',
  `size` smallint NOT NULL DEFAULT '0',
  `wonderGrade` int NOT NULL DEFAULT '-1',
  PRIMARY KEY (`petid`),
  KEY `petid` (`petid`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `playernpcs`
--

DROP TABLE IF EXISTS `playernpcs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `playernpcs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(13) NOT NULL,
  `hair` int NOT NULL,
  `face` int NOT NULL,
  `skin` int NOT NULL,
  `x` int NOT NULL DEFAULT '0',
  `y` int NOT NULL DEFAULT '0',
  `map` int NOT NULL,
  `charid` int NOT NULL,
  `scriptid` int NOT NULL,
  `foothold` int NOT NULL,
  `dir` tinyint(1) NOT NULL DEFAULT '0',
  `gender` tinyint(1) NOT NULL DEFAULT '0',
  `pets` varchar(25) DEFAULT '0,0,0',
  PRIMARY KEY (`id`),
  KEY `scriptid` (`scriptid`),
  KEY `playernpcs_ibfk_1` (`charid`),
  CONSTRAINT `playernpcs_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `playernpcs_equip`
--

DROP TABLE IF EXISTS `playernpcs_equip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `playernpcs_equip` (
  `id` int NOT NULL AUTO_INCREMENT,
  `npcid` int NOT NULL,
  `equipid` int NOT NULL,
  `equippos` int NOT NULL,
  `charid` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `playernpcs_equip_ibfk_1` (`charid`),
  KEY `playernpcs_equip_ibfk_2` (`npcid`),
  CONSTRAINT `playernpcs_equip_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `playernpcs_equip_ibfk_2` FOREIGN KEY (`npcid`) REFERENCES `playernpcs` (`scriptid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `praise_point`
--

DROP TABLE IF EXISTS `praise_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `praise_point` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_id` int NOT NULL DEFAULT '0',
  `total_point` int NOT NULL DEFAULT '0',
  `point` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `KEY` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `praise_point_log`
--

DROP TABLE IF EXISTS `praise_point_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `praise_point_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `from_name` varchar(13) NOT NULL DEFAULT '',
  `from_account_id` int NOT NULL DEFAULT '0',
  `to_name` varchar(13) NOT NULL DEFAULT '',
  `to_account_id` int NOT NULL DEFAULT '0',
  `praise_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `connector_key` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pwreset`
--

DROP TABLE IF EXISTS `pwreset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pwreset` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(14) NOT NULL,
  `email` varchar(100) NOT NULL,
  `confirmkey` varchar(100) NOT NULL,
  `status` tinyint unsigned NOT NULL DEFAULT '0',
  `timestamp` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `questinfo`
--

DROP TABLE IF EXISTS `questinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questinfo` (
  `questinfoid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int NOT NULL DEFAULT '0',
  `quest` int NOT NULL DEFAULT '0',
  `customData` varchar(4000) DEFAULT NULL,
  `date` varchar(20) DEFAULT '',
  PRIMARY KEY (`questinfoid`),
  KEY `characterid` (`characterid`),
  KEY `quest` (`quest`),
  CONSTRAINT `questsinfo_ibfk_1` FOREIGN KEY (`characterid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31775496 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `questinfo_account`
--

DROP TABLE IF EXISTS `questinfo_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questinfo_account` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `account_id` int NOT NULL DEFAULT '0',
  `quest` int NOT NULL DEFAULT '0',
  `customData` varchar(2048) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `account_id` (`account_id`),
  KEY `quest` (`quest`),
  CONSTRAINT `questsinfo_account_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23369408 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `queststatus`
--

DROP TABLE IF EXISTS `queststatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `queststatus` (
  `queststatusid` bigint unsigned NOT NULL AUTO_INCREMENT,
  `characterid` int NOT NULL DEFAULT '0',
  `quest` int NOT NULL DEFAULT '0',
  `status` tinyint NOT NULL DEFAULT '0',
  `time` int NOT NULL DEFAULT '0',
  `forfeited` int NOT NULL DEFAULT '0',
  `customData` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`queststatusid`),
  KEY `characterid` (`characterid`)
) ENGINE=InnoDB AUTO_INCREMENT=172848332 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `queststatusmobs`
--

DROP TABLE IF EXISTS `queststatusmobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `queststatusmobs` (
  `queststatusmobid` int unsigned NOT NULL AUTO_INCREMENT,
  `queststatusid` bigint unsigned NOT NULL DEFAULT '0',
  `mob` int NOT NULL DEFAULT '0',
  `count` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`queststatusmobid`),
  KEY `queststatusid` (`queststatusid`),
  CONSTRAINT `FK_queststatusmobs_queststatus` FOREIGN KEY (`queststatusid`) REFERENCES `queststatus` (`queststatusid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `quick_slot_key_mapped`
--

DROP TABLE IF EXISTS `quick_slot_key_mapped`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quick_slot_key_mapped` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_id` int DEFAULT '0',
  `data` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `KEY` (`player_id`),
  CONSTRAINT `FK_quick_slot_key_mapped_characters` FOREIGN KEY (`player_id`) REFERENCES `characters` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=30244 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `regrocklocations`
--

DROP TABLE IF EXISTS `regrocklocations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regrocklocations` (
  `trockid` int NOT NULL AUTO_INCREMENT,
  `characterid` int DEFAULT NULL,
  `mapid` int DEFAULT NULL,
  PRIMARY KEY (`trockid`),
  KEY `characterid` (`characterid`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reports` (
  `reportid` int NOT NULL AUTO_INCREMENT,
  `characterid` int NOT NULL DEFAULT '0',
  `type` tinyint NOT NULL DEFAULT '0',
  `count` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`reportid`,`characterid`),
  KEY `characterid` (`characterid`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rings`
--

DROP TABLE IF EXISTS `rings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rings` (
  `ringid` bigint NOT NULL DEFAULT '0',
  `partnerRingId` bigint NOT NULL DEFAULT '0',
  `partnerChrId` int NOT NULL DEFAULT '0',
  `itemid` int NOT NULL DEFAULT '0',
  `partnername` varchar(255) NOT NULL,
  PRIMARY KEY (`ringid`),
  KEY `ringid` (`ringid`),
  KEY `partnerChrId` (`partnerChrId`),
  KEY `partnerRingId` (`partnerRingId`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `savedlocations`
--

DROP TABLE IF EXISTS `savedlocations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `savedlocations` (
  `id` int NOT NULL AUTO_INCREMENT,
  `characterid` int NOT NULL,
  `locationtype` int NOT NULL DEFAULT '0',
  `map` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `savedlocations_ibfk_1` (`characterid`),
  CONSTRAINT `savedlocations_ibfk_1` FOREIGN KEY (`characterid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scroll_log`
--

DROP TABLE IF EXISTS `scroll_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scroll_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accId` int NOT NULL DEFAULT '0',
  `chrId` int NOT NULL DEFAULT '0',
  `scrollId` int NOT NULL DEFAULT '0',
  `itemId` int NOT NULL DEFAULT '0',
  `oldSlots` tinyint NOT NULL DEFAULT '0',
  `newSlots` tinyint NOT NULL DEFAULT '0',
  `hammer` tinyint NOT NULL DEFAULT '0',
  `result` varchar(13) NOT NULL DEFAULT '',
  `whiteScroll` tinyint(1) NOT NULL DEFAULT '0',
  `legendarySpirit` tinyint(1) NOT NULL DEFAULT '0',
  `vegaId` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `serialban`
--

DROP TABLE IF EXISTS `serialban`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `serialban` (
  `key` bigint unsigned NOT NULL AUTO_INCREMENT,
  `serialNumber` varchar(100) NOT NULL,
  `memo` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`key`),
  UNIQUE KEY `serialNumber` (`serialNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shop_buy_limit`
--

DROP TABLE IF EXISTS `shop_buy_limit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop_buy_limit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_id` int NOT NULL DEFAULT '0',
  `shop_id` int NOT NULL DEFAULT '0',
  `item_index` int NOT NULL DEFAULT '0',
  `item_id` int NOT NULL DEFAULT '0',
  `buy_count` int NOT NULL DEFAULT '0',
  `buy_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `KEY` (`player_id`,`item_index`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shop_world_buy_limit`
--

DROP TABLE IF EXISTS `shop_world_buy_limit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shop_world_buy_limit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_id` int NOT NULL DEFAULT '0',
  `shop_id` int NOT NULL DEFAULT '0',
  `item_index` int NOT NULL DEFAULT '0',
  `item_id` int NOT NULL DEFAULT '0',
  `buy_count` int NOT NULL DEFAULT '0',
  `buy_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `KEY` (`account_id`,`item_index`)
) ENGINE=InnoDB AUTO_INCREMENT=72665 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shopitems`
--

DROP TABLE IF EXISTS `shopitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shopitems` (
  `shopitemid` int unsigned NOT NULL AUTO_INCREMENT,
  `shopid` int unsigned NOT NULL DEFAULT '0',
  `itemid` int NOT NULL DEFAULT '0',
  `price` int NOT NULL DEFAULT '0',
  `position` int NOT NULL DEFAULT '0',
  `pricequantity` int NOT NULL DEFAULT '0',
  `reqitem` int NOT NULL DEFAULT '0',
  `reqitemq` int NOT NULL DEFAULT '0',
  `rank` int NOT NULL DEFAULT '0',
  `buyable` int NOT NULL DEFAULT '1',
  `category` int NOT NULL DEFAULT '0',
  `quantity` int NOT NULL DEFAULT '1',
  `minLevel` int NOT NULL DEFAULT '0',
  `expiration` int NOT NULL DEFAULT '0',
  `limititem` int unsigned NOT NULL DEFAULT '0',
  `pointQuestEx` int NOT NULL DEFAULT '0',
  `pointPrice` int NOT NULL DEFAULT '0',
  `buyLimit` int NOT NULL DEFAULT '0',
  `worldBuyLimit` int NOT NULL DEFAULT '0',
  `limitQuestExID` int NOT NULL DEFAULT '0',
  `limitQuestExKey` varchar(20) NOT NULL DEFAULT '',
  `limitQuestExValue` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`shopitemid`),
  KEY `shopid` (`shopid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shopranks`
--

DROP TABLE IF EXISTS `shopranks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shopranks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `shopid` int NOT NULL DEFAULT '0',
  `rank` int NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL DEFAULT '',
  `itemid` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shops`
--

DROP TABLE IF EXISTS `shops`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shops` (
  `shopid` int unsigned NOT NULL AUTO_INCREMENT,
  `npcid` int DEFAULT '0',
  PRIMARY KEY (`shopid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `shortcutcmd`
--

DROP TABLE IF EXISTS `shortcutcmd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shortcutcmd` (
  `id` int NOT NULL AUTO_INCREMENT,
  `chrid` int NOT NULL,
  `index` int NOT NULL DEFAULT '0',
  `data` blob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `skill_alarm`
--

DROP TABLE IF EXISTS `skill_alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skill_alarm` (
  `id` int NOT NULL AUTO_INCREMENT,
  `character_id` int NOT NULL DEFAULT '0',
  `alarm_list` varchar(60) NOT NULL DEFAULT '0,0,0,0,0,0',
  `alarm_onoff` varchar(45) NOT NULL DEFAULT '0,0,0,0,0,0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `skillmacros`
--

DROP TABLE IF EXISTS `skillmacros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skillmacros` (
  `id` int NOT NULL AUTO_INCREMENT,
  `characterid` int NOT NULL DEFAULT '0',
  `position` tinyint(1) NOT NULL DEFAULT '0',
  `skill1` int NOT NULL DEFAULT '0',
  `skill2` int NOT NULL DEFAULT '0',
  `skill3` int NOT NULL DEFAULT '0',
  `name` varchar(30) DEFAULT NULL,
  `shout` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `characterid` (`characterid`)
) ENGINE=InnoDB AUTO_INCREMENT=566 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `skills`
--

DROP TABLE IF EXISTS `skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skills` (
  `id` int NOT NULL AUTO_INCREMENT,
  `skillid` int NOT NULL DEFAULT '0',
  `characterid` int NOT NULL DEFAULT '0',
  `skilllevel` int NOT NULL DEFAULT '0',
  `masterlevel` tinyint NOT NULL DEFAULT '0',
  `expiration` bigint NOT NULL DEFAULT '-1',
  PRIMARY KEY (`id`),
  KEY `skills_ibfk_1` (`characterid`),
  KEY `skillid` (`skillid`),
  CONSTRAINT `skills_ibfk_1` FOREIGN KEY (`characterid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7444409 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `skills_cooldowns`
--

DROP TABLE IF EXISTS `skills_cooldowns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skills_cooldowns` (
  `id` int NOT NULL AUTO_INCREMENT,
  `charid` int NOT NULL,
  `SkillID` int NOT NULL,
  `length` bigint NOT NULL,
  `StartTime` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `charid` (`charid`)
) ENGINE=InnoDB AUTO_INCREMENT=117518 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `spawn`
--

DROP TABLE IF EXISTS `spawn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `spawn` (
  `id` int NOT NULL AUTO_INCREMENT,
  `lifeid` int NOT NULL,
  `rx0` int NOT NULL,
  `rx1` int NOT NULL,
  `cy` int NOT NULL,
  `fh` int NOT NULL,
  `type` varchar(11) NOT NULL,
  `dir` int NOT NULL,
  `mapid` int NOT NULL,
  `mobTime` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4309 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `speedladder`
--

DROP TABLE IF EXISTS `speedladder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `speedladder` (
  `round` int NOT NULL AUTO_INCREMENT,
  `right` tinyint NOT NULL DEFAULT '0',
  `line` tinyint NOT NULL DEFAULT '3',
  `odd` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`round`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `speedruns`
--

DROP TABLE IF EXISTS `speedruns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `speedruns` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `type` varchar(13) NOT NULL,
  `leader` varchar(13) NOT NULL,
  `timestring` varchar(1024) NOT NULL,
  `time` bigint NOT NULL DEFAULT '0',
  `members` varchar(1024) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stolen`
--

DROP TABLE IF EXISTS `stolen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stolen` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `characterid` varchar(45) NOT NULL,
  `skillid` varchar(45) NOT NULL,
  `chosen` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=798 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `storages`
--

DROP TABLE IF EXISTS `storages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `storages` (
  `storageid` int unsigned NOT NULL AUTO_INCREMENT,
  `accountid` int NOT NULL DEFAULT '0',
  `slots` int NOT NULL DEFAULT '0',
  `meso` bigint NOT NULL DEFAULT '0',
  PRIMARY KEY (`storageid`),
  KEY `accountid` (`accountid`),
  CONSTRAINT `storages_ibfk_1` FOREIGN KEY (`accountid`) REFERENCES `accounts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=698 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tournamentlog`
--

DROP TABLE IF EXISTS `tournamentlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tournamentlog` (
  `logid` int NOT NULL AUTO_INCREMENT,
  `winnerid` int NOT NULL DEFAULT '0',
  `numContestants` int NOT NULL DEFAULT '0',
  `when` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`logid`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `trocklocations`
--

DROP TABLE IF EXISTS `trocklocations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trocklocations` (
  `trockid` int NOT NULL AUTO_INCREMENT,
  `characterid` int DEFAULT NULL,
  `mapid` int DEFAULT NULL,
  PRIMARY KEY (`trockid`),
  KEY `characterid` (`characterid`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vmatrixslot`
--

DROP TABLE IF EXISTS `vmatrixslot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vmatrixslot` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_id` int DEFAULT '0',
  `index` int DEFAULT '0',
  `slot_enforcement` int DEFAULT '0',
  `released` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `KEY` (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=451326 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `weekly_items`
--

DROP TABLE IF EXISTS `weekly_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `weekly_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `SN` int DEFAULT '0',
  `item_type` tinyint(1) DEFAULT '0',
  `item_id` int DEFAULT '0',
  `item_count` int DEFAULT '0',
  `remain_count` int DEFAULT '0',
  `start_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `end_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `KEY` (`SN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `weekly_quest`
--

DROP TABLE IF EXISTS `weekly_quest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `weekly_quest` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_id` int DEFAULT '0',
  `quest_id` int DEFAULT '0',
  `type` int DEFAULT '0',
  `need_id` int DEFAULT '0',
  `need_quantity` int DEFAULT '0',
  `quantity` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `KEY` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wild_hunter_info`
--

DROP TABLE IF EXISTS `wild_hunter_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wild_hunter_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `player_id` int DEFAULT '0',
  `riding_type` int DEFAULT '0',
  `captured_mob_1` int DEFAULT '0',
  `captured_mob_2` int DEFAULT '0',
  `captured_mob_3` int DEFAULT '0',
  `captured_mob_4` int DEFAULT '0',
  `captured_mob_5` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `KEY` (`player_id`)
) ENGINE=InnoDB AUTO_INCREMENT=406 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wishlist`
--

DROP TABLE IF EXISTS `wishlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wishlist` (
  `characterid` int NOT NULL,
  `sn` int NOT NULL,
  KEY `characterid` (`characterid`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wz_itemadddata`
--

DROP TABLE IF EXISTS `wz_itemadddata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wz_itemadddata` (
  `id` int NOT NULL AUTO_INCREMENT,
  `itemid` int NOT NULL,
  `key` varchar(30) NOT NULL,
  `subKey` varchar(30) NOT NULL,
  `value` varchar(90) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=275094 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wz_itemdata`
--

DROP TABLE IF EXISTS `wz_itemdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wz_itemdata` (
  `itemid` int NOT NULL,
  `name` varchar(1000) COLLATE utf8mb3_bin DEFAULT NULL,
  `msg` varchar(4096) COLLATE utf8mb3_bin DEFAULT NULL,
  `desc` varchar(4096) COLLATE utf8mb3_bin DEFAULT NULL,
  `slotMax` smallint NOT NULL DEFAULT '1',
  `price` varchar(255) COLLATE utf8mb3_bin NOT NULL DEFAULT '-1.0',
  `wholePrice` int NOT NULL DEFAULT '-1',
  `stateChange` int NOT NULL DEFAULT '0',
  `flags` smallint NOT NULL DEFAULT '0',
  `karma` tinyint(1) NOT NULL DEFAULT '0',
  `meso` int NOT NULL DEFAULT '0',
  `monsterBook` int NOT NULL DEFAULT '0',
  `itemMakeLevel` smallint NOT NULL DEFAULT '0',
  `questId` int NOT NULL DEFAULT '0',
  `scrollReqs` text COLLATE utf8mb3_bin,
  `consumeItem` tinytext COLLATE utf8mb3_bin,
  `totalprob` int NOT NULL DEFAULT '0',
  `incSkill` varchar(255) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `replaceid` int NOT NULL DEFAULT '0',
  `replacemsg` varchar(255) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `create` int NOT NULL DEFAULT '0',
  `afterImage` varchar(255) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `option1` int DEFAULT '0',
  `option2` int DEFAULT '0',
  `option3` int unsigned DEFAULT '0',
  `forceUpgrade` int unsigned DEFAULT NULL,
  `bossReward` int DEFAULT '0',
  `nickSkill` int DEFAULT '0',
  `nickSkillTimeLimited` int DEFAULT '0',
  `jokerToSetItem` int DEFAULT '0',
  PRIMARY KEY (`itemid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wz_itemequipdata`
--

DROP TABLE IF EXISTS `wz_itemequipdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wz_itemequipdata` (
  `id` int NOT NULL AUTO_INCREMENT,
  `itemid` int NOT NULL,
  `itemLevel` int NOT NULL DEFAULT '-1',
  `key` varchar(30) NOT NULL,
  `value` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=36035720 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wz_itemrewarddata`
--

DROP TABLE IF EXISTS `wz_itemrewarddata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wz_itemrewarddata` (
  `id` int NOT NULL AUTO_INCREMENT,
  `itemid` int NOT NULL,
  `item` int NOT NULL,
  `prob` int NOT NULL DEFAULT '0',
  `quantity` smallint NOT NULL DEFAULT '0',
  `period` int NOT NULL DEFAULT '-1',
  `worldMsg` varchar(255) NOT NULL DEFAULT '',
  `effect` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1702813 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wz_mobskilldata`
--

DROP TABLE IF EXISTS `wz_mobskilldata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wz_mobskilldata` (
  `id` int NOT NULL AUTO_INCREMENT,
  `skillid` int NOT NULL,
  `level` int NOT NULL,
  `hp` int NOT NULL DEFAULT '100',
  `mpcon` int NOT NULL DEFAULT '0',
  `x` int NOT NULL DEFAULT '1',
  `y` int NOT NULL DEFAULT '1',
  `time` int NOT NULL DEFAULT '0',
  `prop` int NOT NULL DEFAULT '100',
  `limit` int NOT NULL DEFAULT '0',
  `spawneffect` int NOT NULL DEFAULT '0',
  `interval` int NOT NULL DEFAULT '0',
  `summons` varchar(1024) NOT NULL DEFAULT '',
  `ltx` int NOT NULL DEFAULT '0',
  `lty` int NOT NULL DEFAULT '0',
  `rbx` int NOT NULL DEFAULT '0',
  `rby` int NOT NULL DEFAULT '0',
  `once` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=79786 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wz_oxdata`
--

DROP TABLE IF EXISTS `wz_oxdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wz_oxdata` (
  `questionset` smallint NOT NULL DEFAULT '0',
  `questionid` smallint NOT NULL DEFAULT '0',
  `question` varchar(200) NOT NULL DEFAULT '',
  `display` varchar(200) NOT NULL DEFAULT '',
  `answer` enum('o','x') NOT NULL,
  PRIMARY KEY (`questionset`,`questionid`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wz_questactdata`
--

DROP TABLE IF EXISTS `wz_questactdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wz_questactdata` (
  `id` int NOT NULL AUTO_INCREMENT,
  `questid` int NOT NULL DEFAULT '0',
  `type` int NOT NULL DEFAULT '0',
  `name` varchar(127) NOT NULL DEFAULT '',
  `intStore` int NOT NULL DEFAULT '0',
  `applicableJobs` text NOT NULL,
  `uniqueid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `questid` (`questid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100568 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wz_questactitemdata`
--

DROP TABLE IF EXISTS `wz_questactitemdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wz_questactitemdata` (
  `id` int NOT NULL AUTO_INCREMENT,
  `itemid` int NOT NULL DEFAULT '0',
  `count` smallint NOT NULL DEFAULT '0',
  `period` int NOT NULL DEFAULT '0',
  `gender` tinyint(1) NOT NULL DEFAULT '2',
  `job` int NOT NULL DEFAULT '-1',
  `jobEx` int NOT NULL DEFAULT '-1',
  `prop` int NOT NULL DEFAULT '-1',
  `uniqueid` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=84680 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wz_questactquestdata`
--

DROP TABLE IF EXISTS `wz_questactquestdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wz_questactquestdata` (
  `id` int NOT NULL AUTO_INCREMENT,
  `quest` int NOT NULL DEFAULT '0',
  `state` tinyint(1) NOT NULL DEFAULT '2',
  `uniqueid` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=191 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wz_questactskilldata`
--

DROP TABLE IF EXISTS `wz_questactskilldata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wz_questactskilldata` (
  `id` int NOT NULL AUTO_INCREMENT,
  `skillid` int NOT NULL DEFAULT '0',
  `skillLevel` int NOT NULL DEFAULT '-1',
  `masterLevel` int NOT NULL DEFAULT '-1',
  `uniqueid` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1763 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wz_questdata`
--

DROP TABLE IF EXISTS `wz_questdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wz_questdata` (
  `questid` int NOT NULL,
  `name` varchar(1024) COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `autoStart` tinyint(1) NOT NULL DEFAULT '0',
  `autoPreComplete` tinyint(1) NOT NULL DEFAULT '0',
  `viewMedalItem` int NOT NULL DEFAULT '0',
  `selectedSkillID` int NOT NULL DEFAULT '0',
  `blocked` tinyint(1) NOT NULL DEFAULT '0',
  `autoAccept` tinyint(1) NOT NULL DEFAULT '0',
  `autoComplete` tinyint(1) NOT NULL DEFAULT '0',
  `selfStart` tinyint(1) NOT NULL DEFAULT '0',
  `selfComplete` tinyint(1) NOT NULL DEFAULT '0',
  `startNavi` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`questid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wz_questpartydata`
--

DROP TABLE IF EXISTS `wz_questpartydata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wz_questpartydata` (
  `id` int NOT NULL AUTO_INCREMENT,
  `questid` int NOT NULL DEFAULT '0',
  `rank` varchar(1) NOT NULL DEFAULT '',
  `mode` varchar(13) NOT NULL DEFAULT '',
  `property` varchar(255) NOT NULL DEFAULT '',
  `value` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `quests_ibfk_7` (`questid`)
) ENGINE=MyISAM AUTO_INCREMENT=401 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wz_questreqdata`
--

DROP TABLE IF EXISTS `wz_questreqdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wz_questreqdata` (
  `id` int NOT NULL AUTO_INCREMENT,
  `questid` int NOT NULL DEFAULT '0',
  `name` varchar(127) NOT NULL DEFAULT '',
  `type` tinyint(1) NOT NULL DEFAULT '0',
  `stringStore` varchar(1024) NOT NULL DEFAULT '',
  `intStoresFirst` varchar(1024) NOT NULL DEFAULT '',
  `intStoresSecond` varchar(4096) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `quests_ibfk_1` (`questid`)
) ENGINE=MyISAM AUTO_INCREMENT=803648 DEFAULT CHARSET=euckr;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-08 18:44:10
