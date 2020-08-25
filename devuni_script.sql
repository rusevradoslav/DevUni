CREATE DATABASE  IF NOT EXISTS `devuni_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `devuni_db`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: devuni_db
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `about_me`
--

DROP TABLE IF EXISTS `about_me`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `about_me` (
  `id` varchar(255) NOT NULL,
  `knowledge_level` varchar(30) DEFAULT NULL,
  `self_description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `about_me`
--

LOCK TABLES `about_me` WRITE;
/*!40000 ALTER TABLE `about_me` DISABLE KEYS */;
INSERT INTO `about_me` VALUES ('2aca96e5-930a-4576-8f42-aec5cdaa88c3','IT Specialist','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper'),('d76d586d-e438-4060-8a13-555b0cf34d59','C# Master','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper'),('fb47b1d1-62b1-4114-994b-6b2bcabec631','Java Master ','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper');
/*!40000 ALTER TABLE `about_me` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assigments`
--

DROP TABLE IF EXISTS `assigments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `assigments` (
  `id` varchar(255) NOT NULL,
  `checked` bit(1) DEFAULT NULL,
  `checked_on` datetime(6) DEFAULT NULL,
  `description` text,
  `grade_percentage` double DEFAULT NULL,
  `file_id` varchar(255) DEFAULT NULL,
  `lecture_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnjehej8tt9n90vpe1mp63r4bh` (`file_id`),
  KEY `FKr8jsgpe4f0jmljptc5ithepwj` (`lecture_id`),
  KEY `FKlploi0v887om7lem7qrv5nwmm` (`user_id`),
  CONSTRAINT `FKlploi0v887om7lem7qrv5nwmm` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKnjehej8tt9n90vpe1mp63r4bh` FOREIGN KEY (`file_id`) REFERENCES `files` (`id`),
  CONSTRAINT `FKr8jsgpe4f0jmljptc5ithepwj` FOREIGN KEY (`lecture_id`) REFERENCES `lectures` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assigments`
--

LOCK TABLES `assigments` WRITE;
/*!40000 ALTER TABLE `assigments` DISABLE KEYS */;
INSERT INTO `assigments` VALUES ('003895c4-e53b-47c1-ad2d-1365e75d4b2f',_binary '','2020-08-24 14:19:26.863195','Description:rusevrado Lecture 2 file Php Master Class',100,'1af86404-fca6-447a-9ca7-d582843f47a9','fd5c01a4-c7b0-4560-bd8d-e132db433239','ced4690d-7718-4f3a-b9f6-5cd398f54a19'),('14e81d6b-a63b-4c29-8d52-cf8c9329c425',_binary '','2020-08-24 14:17:55.192637','Description:rusevrado Lecture 2 file JavaScript Basics',100,'d802785b-039c-4c50-85f3-e19509009d05','c59cf434-b18f-4781-ac8d-241fece0b09f','ced4690d-7718-4f3a-b9f6-5cd398f54a19'),('4666fdc2-8179-418e-b717-ccabe9c6b02a',_binary '','2020-08-24 14:19:58.034274','Description:rusevrado Lecture 1 file C++ Master Class',100,'d6fa039a-b9b3-4c30-bfd4-7f6048221c83','58913245-2e33-4902-b675-ce2c9c614687','ced4690d-7718-4f3a-b9f6-5cd398f54a19'),('5315dc06-19b1-4527-98b1-d00290d7e612',_binary '','2020-08-24 13:13:27.925813','Description: rusevrado Stack And Queues file',100,'9c47bf4b-f678-4a8c-850e-ef81d805d74e','250a14ae-d55e-412c-889a-0f1df11fe229','ced4690d-7718-4f3a-b9f6-5cd398f54a19'),('66bb6112-a99d-4540-a868-8b7bb7e5be66',_binary '','2020-08-24 14:19:22.052675','Description:rusevrado Lecture 1 file Php Master Class',100,'c0307466-59fa-44c3-ab68-ab428736619e','369f5538-7ada-47bb-a08f-aadabf1e652b','ced4690d-7718-4f3a-b9f6-5cd398f54a19'),('70db35fb-ef69-47ca-967d-38df6157bfe2',_binary '','2020-08-24 14:19:53.364625','Description:rusevrado Lecture 2 file C++ Master Class',100,'68dd7579-75ab-48bf-bfeb-1ecc791988d7','173a3896-ae88-4dc8-acfc-d5455d10c7d4','ced4690d-7718-4f3a-b9f6-5cd398f54a19'),('8b370f7d-ece8-4cc5-81c2-14d341d21dda',_binary '','2020-08-24 13:13:39.843317','Description: rusevrado Multidimensional Arrays file',100,'22b81ee8-31d8-4180-8420-6e67912d741d','ec61015c-82d4-4969-a133-fc1e4ad45fc2','ced4690d-7718-4f3a-b9f6-5cd398f54a19'),('9cdfe881-90b0-4df1-8857-7fb37d833f84',_binary '','2020-08-24 13:32:06.613449','Description: rusevrado Lecture 1 file',100,'c232b0b5-9c3b-45d3-b7bf-edd5b6d8d978','e6a5e120-7de7-4f01-9d1e-d944dc74436e','ced4690d-7718-4f3a-b9f6-5cd398f54a19'),('a0b9428e-5a97-4d07-87ef-478fd7b2a97f',_binary '','2020-08-24 13:31:54.308783','Description: rusevrado Lecture 2 file',100,'7d6e30cf-99aa-444a-a13a-50bba964ba86','112e538a-dab5-4c8b-b789-ed905b88c3aa','ced4690d-7718-4f3a-b9f6-5cd398f54a19'),('a9493b78-1c85-4866-8181-efc8a06d9f62',_binary '','2020-08-24 14:17:49.877142','Description:rusevrado Lecture 1 file JavaScript Basics',100,'31b2ef80-c790-4db4-8830-78c7d56b13ca','117c54e4-9ee4-4c81-978d-17a0466b20be','ced4690d-7718-4f3a-b9f6-5cd398f54a19'),('ae8cd1e2-ad31-42ad-8f3a-ac85164cf21b',_binary '','2020-08-24 13:32:00.455639','Description: rusevrado Lecture 3 file',100,'3df78389-7670-4784-ba2c-3557f233b7e1','d571ebbb-b755-4ed3-aaf3-bb03d498bfef','ced4690d-7718-4f3a-b9f6-5cd398f54a19'),('f8e6cfe8-fc24-49df-a303-ee3103362387',_binary '','2020-08-24 14:18:42.785129','Description:rusevrado Lecture 1 file Python Master Class',100,'7367ace2-4468-41f1-b228-2db6b74207a6','41c00180-3571-45ee-8f04-f8ab9d3ddf14','ced4690d-7718-4f3a-b9f6-5cd398f54a19'),('fc8a2e9a-6a38-4b73-bc29-1310d4b203a8',_binary '','2020-08-24 13:13:33.574180','Description: rusevrado Functional Programming file',100,'708d278c-df2f-4e18-96f1-c9ed1f72fbfa','c8680a6f-bd8a-440b-ade5-8b01456bc29a','ced4690d-7718-4f3a-b9f6-5cd398f54a19');
/*!40000 ALTER TABLE `assigments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `authorities` (
  `id` varchar(255) NOT NULL,
  `authority` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_q0u5f2cdlshec8tlh6818bhbk` (`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` VALUES ('7a9e9a8e-a94c-4ce9-af0e-fbeb8a1fbaf4','ROLE_ADMIN'),('ff2540ad-4dc9-4421-a49e-38aab5389c5b','ROLE_ROOT_ADMIN'),('964b28b1-f093-4b8e-ab68-c39ef412030f','ROLE_STUDENT'),('ad885761-7304-4cd9-9e06-0ca82fa1c71d','ROLE_TEACHER');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses`
--

DROP TABLE IF EXISTS `courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `courses` (
  `id` varchar(255) NOT NULL,
  `course_photo` varchar(255) DEFAULT NULL,
  `course_rating` int(11) DEFAULT NULL,
  `description` text NOT NULL,
  `difficulty` int(11) NOT NULL,
  `duration_weeks` int(11) NOT NULL,
  `endedon` datetime(6) NOT NULL,
  `pass_percentage` double DEFAULT NULL,
  `short_description` text NOT NULL,
  `started_on` datetime(6) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `title` varchar(40) NOT NULL,
  `author_id` varchar(255) DEFAULT NULL,
  `topic_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhbo41uaq9qyi5ora71hq2oyah` (`author_id`),
  KEY `FKlvrhaosps2wkj26qi1nmomd4p` (`topic_id`),
  CONSTRAINT `FKhbo41uaq9qyi5ora71hq2oyah` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKlvrhaosps2wkj26qi1nmomd4p` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses`
--

LOCK TABLES `courses` WRITE;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
INSERT INTO `courses` VALUES ('02249be3-12cb-45d6-8894-b78a6f317748','https://res.cloudinary.com/devuni/image/upload/v1598262709/rsz_1546776530_xubtbg.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,4,'2022-01-21 11:20:00.000000',90,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2021-12-24 11:20:00.000000',_binary '','Java Advanced 0.1','1c746291-f197-4ae6-bb85-81532774360f','1b27a86d-aa32-4663-8fa1-fba300724cec'),('1080dbe4-c0b3-4d3c-bc7b-ab7408f0f4ec','https://res.cloudinary.com/devuni/image/upload/v1598263455/rsz_learn-c-sharp-programming_np8kif.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,4,'2022-01-21 11:49:00.000000',90,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2021-12-24 11:49:00.000000',_binary '','C# Master Class','83bee6ab-228d-4e25-bf00-3c9c315f8cc6','381766bd-60e0-487f-8a0d-4105cbf06804'),('190a2f3c-0ce8-4243-9444-f8520095576a','https://res.cloudinary.com/devuni/image/upload/v1598262709/rsz_1546776530_xubtbg.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,4,'2022-01-21 11:18:00.000000',90,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2021-12-24 11:18:00.000000',_binary '','Java Master Class 0.1','1c746291-f197-4ae6-bb85-81532774360f','1b27a86d-aa32-4663-8fa1-fba300724cec'),('23557302-fb54-4d1f-bb2c-65be5a9afded','https://res.cloudinary.com/devuni/image/upload/v1598263600/rsz_1026276_b23a_5_aw7bcq.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,4,'2021-02-21 12:33:00.000000',5,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2021-01-24 12:33:00.000000',_binary '','C++ Advanced','83bee6ab-228d-4e25-bf00-3c9c315f8cc6','842f38af-fbf9-43bb-8ce2-cfd59543c071'),('290b4790-1def-4584-9b04-e9a934cad123','https://res.cloudinary.com/devuni/image/upload/v1598262709/rsz_1546776530_xubtbg.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,4,'2021-02-21 11:22:00.000000',90,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2021-01-24 11:22:00.000000',_binary '','Java Web','1c746291-f197-4ae6-bb85-81532774360f','1b27a86d-aa32-4663-8fa1-fba300724cec'),('3fa33a71-51b7-4955-aa48-f3ff1cac812a','https://res.cloudinary.com/devuni/image/upload/v1598262709/rsz_1546776530_xubtbg.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,4,'2022-01-21 11:05:00.000000',90,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2021-12-24 11:05:00.000000',_binary '\0','Java Master Class','1c746291-f197-4ae6-bb85-81532774360f','1b27a86d-aa32-4663-8fa1-fba300724cec'),('3fdb5b3e-3545-4158-9e54-f31ea34c48f1','http://res.cloudinary.com/devuni/image/upload/v1598262152/cx9v4fbqwkn01henbdqh.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,4,'2021-06-21 12:42:00.000000',90,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2021-05-24 12:42:00.000000',_binary '','Php Master Class','43ad28cc-56e4-4e48-80ad-132395276513','034c0db1-bbf0-4db4-b096-dc4a8d91cc8a'),('41072a72-bacd-455b-98a8-bca7d24e3772','https://res.cloudinary.com/devuni/image/upload/v1598263137/1743420_0062_bnakub.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,4,'2021-02-21 11:47:00.000000',90,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2021-01-24 11:47:00.000000',_binary '','JavaScript Basics','1c746291-f197-4ae6-bb85-81532774360f','ccada0f9-6dc3-4b19-8232-64366c153e89'),('60420785-7678-4988-aa3a-70da62148aba','http://res.cloudinary.com/devuni/image/upload/v1598261747/qq1dhxna0qz7lpchnzxh.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,4,'2022-02-21 12:35:00.000000',90,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2022-01-24 12:35:00.000000',_binary '','Python Master Class','43ad28cc-56e4-4e48-80ad-132395276513','4a550ebb-7f63-4b5a-b209-a38b92fce534'),('77ac11d6-6b4a-40fc-a18b-78bbc4ad596b','https://res.cloudinary.com/devuni/image/upload/v1598262709/rsz_1546776530_xubtbg.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,4,'2022-01-21 11:08:00.000000',90,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2021-12-24 11:08:00.000000',_binary '','Java Basics','1c746291-f197-4ae6-bb85-81532774360f','1b27a86d-aa32-4663-8fa1-fba300724cec'),('8797024f-9e7d-4f2c-b532-45e0019aacb5','https://res.cloudinary.com/devuni/image/upload/v1598263455/rsz_learn-c-sharp-programming_np8kif.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,4,'2021-02-21 12:28:00.000000',90,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2021-01-24 12:28:00.000000',_binary '','C# Advanced','83bee6ab-228d-4e25-bf00-3c9c315f8cc6','381766bd-60e0-487f-8a0d-4105cbf06804'),('8b112490-65c0-4997-b394-a8e15c40320d','https://res.cloudinary.com/devuni/image/upload/v1598263137/1743420_0062_bnakub.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,4,'2021-02-21 11:43:00.000000',90,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2021-01-24 11:43:00.000000',_binary '','JavaScript Master Class','1c746291-f197-4ae6-bb85-81532774360f','ccada0f9-6dc3-4b19-8232-64366c153e89'),('bee2cdaa-f561-438a-9420-222c86b1b5c1','https://res.cloudinary.com/devuni/image/upload/v1598262709/rsz_1546776530_xubtbg.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,4,'2022-01-21 11:12:00.000000',90,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2021-12-24 11:12:00.000000',_binary '\0','Java Master Class','1c746291-f197-4ae6-bb85-81532774360f','1b27a86d-aa32-4663-8fa1-fba300724cec'),('c63afea2-a503-4c3f-8093-f7d1c1d83e99','https://res.cloudinary.com/devuni/image/upload/v1598263600/rsz_1026276_b23a_5_aw7bcq.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,3,'2021-02-14 12:30:00.000000',90,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2021-01-24 12:30:00.000000',_binary '','C++ Master Class','83bee6ab-228d-4e25-bf00-3c9c315f8cc6','842f38af-fbf9-43bb-8ce2-cfd59543c071'),('e370e85c-63cc-46f7-88bd-f3ebd2947faa','https://res.cloudinary.com/devuni/image/upload/v1598262709/rsz_1546776530_xubtbg.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,4,'2020-09-25 11:15:00.000000',90,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2020-08-28 11:15:00.000000',_binary '\0','Java Advanced','1c746291-f197-4ae6-bb85-81532774360f','1b27a86d-aa32-4663-8fa1-fba300724cec'),('f92f46ca-033b-4fe7-a41b-f8efa4212ec5','https://res.cloudinary.com/devuni/image/upload/v1598262709/rsz_1546776530_xubtbg.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,4,'2022-01-21 11:06:00.000000',90,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2021-12-24 11:06:00.000000',_binary '\0','Java Advanced','1c746291-f197-4ae6-bb85-81532774360f','1b27a86d-aa32-4663-8fa1-fba300724cec'),('fdb62d51-1b31-4f7d-b813-2ae0e2924f2c','http://res.cloudinary.com/devuni/image/upload/v1598262004/b3lbnbw5wfwln7ttvkat.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,4,'2021-06-21 12:38:00.000000',90,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2021-05-24 12:38:00.000000',_binary '','Python Advanced','43ad28cc-56e4-4e48-80ad-132395276513','4a550ebb-7f63-4b5a-b209-a38b92fce534'),('fea4d752-0112-49a0-994e-6b1e4c6c32d7','https://res.cloudinary.com/devuni/image/upload/v1598262709/rsz_1546776530_xubtbg.jpg',0,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.',2,4,'2022-01-21 11:20:00.000000',90,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','2021-12-24 11:20:00.000000',_binary '','Java DB','1c746291-f197-4ae6-bb85-81532774360f','1b27a86d-aa32-4663-8fa1-fba300724cec');
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses_enrolled_students`
--

DROP TABLE IF EXISTS `courses_enrolled_students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `courses_enrolled_students` (
  `enrolled_courses_id` varchar(255) NOT NULL,
  `enrolled_students_id` varchar(255) NOT NULL,
  KEY `FK2cld6h8p02obq2tikc5b88vut` (`enrolled_students_id`),
  KEY `FK3g8h407ej4wsben7ipbptr7c2` (`enrolled_courses_id`),
  CONSTRAINT `FK2cld6h8p02obq2tikc5b88vut` FOREIGN KEY (`enrolled_students_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK3g8h407ej4wsben7ipbptr7c2` FOREIGN KEY (`enrolled_courses_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses_enrolled_students`
--

LOCK TABLES `courses_enrolled_students` WRITE;
/*!40000 ALTER TABLE `courses_enrolled_students` DISABLE KEYS */;
INSERT INTO `courses_enrolled_students` VALUES ('190a2f3c-0ce8-4243-9444-f8520095576a','ced4690d-7718-4f3a-b9f6-5cd398f54a19'),('1080dbe4-c0b3-4d3c-bc7b-ab7408f0f4ec','ced4690d-7718-4f3a-b9f6-5cd398f54a19'),('c63afea2-a503-4c3f-8093-f7d1c1d83e99','ced4690d-7718-4f3a-b9f6-5cd398f54a19'),('41072a72-bacd-455b-98a8-bca7d24e3772','ced4690d-7718-4f3a-b9f6-5cd398f54a19'),('60420785-7678-4988-aa3a-70da62148aba','ced4690d-7718-4f3a-b9f6-5cd398f54a19'),('3fdb5b3e-3545-4158-9e54-f31ea34c48f1','ced4690d-7718-4f3a-b9f6-5cd398f54a19');
/*!40000 ALTER TABLE `courses_enrolled_students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `files`
--

DROP TABLE IF EXISTS `files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `files` (
  `id` varchar(255) NOT NULL,
  `data` longblob,
  `file_name` varchar(255) DEFAULT NULL,
  `file_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `files`
--

LOCK TABLES `files` WRITE;
/*!40000 ALTER TABLE `files` DISABLE KEYS */;
INSERT INTO `files` VALUES ('00eb5fc9-e5ff-48c1-b89f-a3fc231a9be5',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('11a9a002-432e-4e03-a997-e340e4545e65',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('1af86404-fca6-447a-9ca7-d582843f47a9',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','ASSIGNMENT.zip','application/x-zip-compressed'),('22b81ee8-31d8-4180-8420-6e67912d741d',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','ASSIGNMENT.zip','application/x-zip-compressed'),('23463b53-975c-4166-b903-1ca8d787251f',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('2fd181c9-b668-4dbe-9150-cc405847962b',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('31b2ef80-c790-4db4-8830-78c7d56b13ca',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','JAVA_SCRIPT.zip','application/x-zip-compressed'),('33c91825-d1f0-41cb-8a3f-7d515a518e81',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('3408214a-909c-49a6-9e20-1205a2730f49',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('3a3c3473-d887-46b0-9943-8183a3e79221',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('3df78389-7670-4784-ba2c-3557f233b7e1',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','ASSIGNMENT.zip','application/x-zip-compressed'),('494fcaff-90b4-4de9-9957-9a1d3d5ae7d6',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('4e03b04c-ae45-4e98-9aab-ea371906ad1c',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('51861265-2d39-49a0-8e29-7c90706aea63',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('56bd5325-36d4-4712-9ab5-b4db415d1d8e',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('58852fed-9478-47f8-be10-213769d77d18',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('68dd7579-75ab-48bf-bfeb-1ecc791988d7',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','ASSIGNMENT.zip','application/x-zip-compressed'),('6d0d81e4-e7cd-4947-b93b-9c070aecff5a',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('708d278c-df2f-4e18-96f1-c9ed1f72fbfa',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','ASSIGNMENT.zip','application/x-zip-compressed'),('7367ace2-4468-41f1-b228-2db6b74207a6',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','ASSIGNMENT.zip','application/x-zip-compressed'),('74cf3d92-f17d-44ed-9e26-f0dc10bcd264',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('763a44c1-3ba7-4ec8-99a0-58a4f8d4ba64',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('79db5706-8551-4db5-b949-ed9f6262a599',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('7a9a0149-7262-48b3-a563-98b77fbc6efd',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('7d6e30cf-99aa-444a-a13a-50bba964ba86',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','ASSIGNMENT.zip','application/x-zip-compressed'),('888b5fd7-c467-46d4-8748-09b4f66add71',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('88f012cf-9820-4ea0-8663-1f72d2b973e6',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('89c7ad78-572d-46e0-9c6d-1ddbae55cbb5',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('89d37033-71a4-4747-ab78-ebc9f990136e',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('8c9c2137-008e-4c57-ae81-3e2e755bd7e4',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('9230903c-fd08-4540-83a7-39a26d94d930',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('9c47bf4b-f678-4a8c-850e-ef81d805d74e',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','ASSIGNMENT.zip','application/x-zip-compressed'),('b263a5fc-e9a6-4489-b6a1-1219eba1bb8f',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('b6b6f72e-7f3f-4120-81ae-8c463b2383ce',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('c0307466-59fa-44c3-ab68-ab428736619e',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','ASSIGNMENT.zip','application/x-zip-compressed'),('c232b0b5-9c3b-45d3-b7bf-edd5b6d8d978',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','JAVA_SCRIPT.zip','application/x-zip-compressed'),('c2a1d022-175c-4cf6-8996-813b586f989f',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('c5b8e810-79d7-40ca-96cb-83e52dbaa4a5',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('ce091371-f89b-4d36-9f28-41b0c06cec89',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('d6b31b9a-e1e6-4844-b909-e92c083959ce',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('d6fa039a-b9b3-4c30-bfd4-7f6048221c83',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','ASSIGNMENT.zip','application/x-zip-compressed'),('d802785b-039c-4c50-85f3-e19509009d05',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','JAVA_SCRIPT.zip','application/x-zip-compressed'),('db9afdf2-2f19-4e58-ae0e-3f71cb5b9204',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('dba6c768-9aad-4f72-9523-1017eb9102d4',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('e41e6627-16fd-4c5b-a86f-934e2b0fcd51',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('e74d8b9e-3bdc-4fdc-bbe3-0765c127af5e',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed'),('facc7419-54c0-4d6a-9f63-5d7e21bd2390',_binary 'PK\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0:[Q\0\0\0\0\0\0\0\0\0\0\0\0\r\0\0\0\0\0\0\0\0\0 \0\0\0\0\0\0\0HOMEWORK.docxPK\0\0\0\0\0\0;\0\0\0+\0\0\0\0\0','HOMEWORK.zip','application/x-zip-compressed');
/*!40000 ALTER TABLE `files` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lectures`
--

DROP TABLE IF EXISTS `lectures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `lectures` (
  `id` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `lecture_video_url` text NOT NULL,
  `title` varchar(40) NOT NULL,
  `course_id` varchar(255) DEFAULT NULL,
  `resources_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsj4m8ipr4qnehoyxk7kbu3ide` (`course_id`),
  KEY `FK6efyydkcfda0h2ffi5vnk0eb3` (`resources_id`),
  CONSTRAINT `FK6efyydkcfda0h2ffi5vnk0eb3` FOREIGN KEY (`resources_id`) REFERENCES `files` (`id`),
  CONSTRAINT `FKsj4m8ipr4qnehoyxk7kbu3ide` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lectures`
--

LOCK TABLES `lectures` WRITE;
/*!40000 ALTER TABLE `lectures` DISABLE KEYS */;
INSERT INTO `lectures` VALUES ('0d4df849-45ec-40d8-84ff-29908dab1838','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','her_7pa0vrg','Unit Testing','290b4790-1def-4584-9b04-e9a934cad123','6d0d81e4-e7cd-4947-b93b-9c070aecff5a'),('0dfca9eb-c043-4656-8bb0-b5c2e2077415','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','W6NZfCO5SIk','Lecture 1','8b112490-65c0-4997-b394-a8e15c40320d','3408214a-909c-49a6-9e20-1205a2730f49'),('112e538a-dab5-4c8b-b789-ed905b88c3aa','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','GhQdlIFylQ8','Lecture 2','1080dbe4-c0b3-4d3c-bc7b-ab7408f0f4ec','b6b6f72e-7f3f-4120-81ae-8c463b2383ce'),('117c54e4-9ee4-4c81-978d-17a0466b20be','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','W6NZfCO5SIk','Lecture 1','41072a72-bacd-455b-98a8-bca7d24e3772','88f012cf-9820-4ea0-8663-1f72d2b973e6'),('173a3896-ae88-4dc8-acfc-d5455d10c7d4','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','IIwgeZgZFBo','Lecture 2','c63afea2-a503-4c3f-8093-f7d1c1d83e99','c5b8e810-79d7-40ca-96cb-83e52dbaa4a5'),('20adc785-054f-42fe-9a97-0a6fe7328817','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','GhQdlIFylQ8','Lecture 3','8797024f-9e7d-4f2c-b532-45e0019aacb5','494fcaff-90b4-4de9-9957-9a1d3d5ae7d6'),('250a14ae-d55e-412c-889a-0f1df11fe229','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','JvGZh_BdF-8','Stack And Queues','190a2f3c-0ce8-4243-9444-f8520095576a','b263a5fc-e9a6-4489-b6a1-1219eba1bb8f'),('3131c7bc-1b54-415a-ae02-a226f54116eb','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','eIrMbAQSU34','Java For Beginners','77ac11d6-6b4a-40fc-a18b-78bbc4ad596b','89c7ad78-572d-46e0-9c6d-1ddbae55cbb5'),('369f5538-7ada-47bb-a08f-aadabf1e652b','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','OK_JCtrrv-c','Lecture 1','3fdb5b3e-3545-4158-9e54-f31ea34c48f1','23463b53-975c-4166-b903-1ca8d787251f'),('3ed1212f-ac63-49f9-a0ab-d6715499eb76','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','eI_X_K_XoFs','Spring JPA','fea4d752-0112-49a0-994e-6b1e4c6c32d7','74cf3d92-f17d-44ed-9e26-f0dc10bcd264'),('41c00180-3571-45ee-8f04-f8ab9d3ddf14','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','rfscVS0vtbw','Lecture 1','60420785-7678-4988-aa3a-70da62148aba','7a9a0149-7262-48b3-a563-98b77fbc6efd'),('425f1e46-c318-4553-a753-f410c23c1523','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','rfscVS0vtbw','Lecture 1','fdb62d51-1b31-4f7d-b813-2ae0e2924f2c','c2a1d022-175c-4cf6-8996-813b586f989f'),('519a6c78-1945-49cf-a410-402e1a576463','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','cakN0XC6CcQ','For Loops In Java','77ac11d6-6b4a-40fc-a18b-78bbc4ad596b','2fd181c9-b668-4dbe-9150-cc405847962b'),('58913245-2e33-4902-b675-ce2c9c614687','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','vLnPwxZdW4Y','Lecture 1','c63afea2-a503-4c3f-8093-f7d1c1d83e99','51861265-2d39-49a0-8e29-7c90706aea63'),('6fe54f3c-389e-4578-a935-528e04f93b7e','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','vLnPwxZdW4Y','Lecture 1','23557302-fb54-4d1f-bb2c-65be5a9afded','33c91825-d1f0-41cb-8a3f-7d515a518e81'),('79f53dbb-b659-486e-81d2-0d2bb1b2ed96','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','zjhWPRXfGJ0','Rest Controllers','290b4790-1def-4584-9b04-e9a934cad123','00eb5fc9-e5ff-48c1-b89f-a3fc231a9be5'),('7b00274d-7d21-4fa3-aa92-953f2e2c49be','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','WPvGqX-TXP0','Java Data Types','77ac11d6-6b4a-40fc-a18b-78bbc4ad596b','dba6c768-9aad-4f72-9523-1017eb9102d4'),('937c064f-37c6-4779-935b-c055710ed6e9','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','eI_X_K_XoFs','Spring DB','fea4d752-0112-49a0-994e-6b1e4c6c32d7','e41e6627-16fd-4c5b-a86f-934e2b0fcd51'),('9d109753-55c1-44e6-a85d-11f157b1cc96','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','mN5ib1XasSA','Quick Sort','02249be3-12cb-45d6-8894-b78a6f317748','89d37033-71a4-4747-ab78-ebc9f990136e'),('a615b8b7-e7c3-44ce-9c5e-37f7fee1bf07','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','GhQdlIFylQ8','Lecture 1','8797024f-9e7d-4f2c-b532-45e0019aacb5','ce091371-f89b-4d36-9f28-41b0c06cec89'),('a7441303-9a28-43b1-b529-29742bbd6189','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','W6NZfCO5SIk','Lecture 2','8b112490-65c0-4997-b394-a8e15c40320d','8c9c2137-008e-4c57-ae81-3e2e755bd7e4'),('b74cea2f-d543-4ffc-9493-6085d9873b73','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','GhQdlIFylQ8','Lecture 2','8797024f-9e7d-4f2c-b532-45e0019aacb5','11a9a002-432e-4e03-a997-e340e4545e65'),('b906070e-5ed2-4785-a5cc-51af21673995','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','vLnPwxZdW4Y','Lecture 2','23557302-fb54-4d1f-bb2c-65be5a9afded','56bd5325-36d4-4712-9ab5-b4db415d1d8e'),('c59cf434-b18f-4781-ac8d-241fece0b09f','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','W6NZfCO5SIk','Lecture 2','41072a72-bacd-455b-98a8-bca7d24e3772','3a3c3473-d887-46b0-9943-8183a3e79221'),('c85bda71-64ba-404c-90ef-aa3d4b22a989','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','IlRyO9dXsYE','Shell Sort ','02249be3-12cb-45d6-8894-b78a6f317748','888b5fd7-c467-46d4-8748-09b4f66add71'),('c8680a6f-bd8a-440b-ade5-8b01456bc29a','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','195KUinjBpU','Functional Programming','190a2f3c-0ce8-4243-9444-f8520095576a','79db5706-8551-4db5-b949-ed9f6262a599'),('d571ebbb-b755-4ed3-aaf3-bb03d498bfef','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','GhQdlIFylQ8','Lecture 3','1080dbe4-c0b3-4d3c-bc7b-ab7408f0f4ec','4e03b04c-ae45-4e98-9aab-ea371906ad1c'),('e6a5e120-7de7-4f01-9d1e-d944dc74436e','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','GhQdlIFylQ8','Lecture 1','1080dbe4-c0b3-4d3c-bc7b-ab7408f0f4ec','9230903c-fd08-4540-83a7-39a26d94d930'),('e929fbcc-bdcd-4dd4-837e-1e85a5d1e216','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','her_7pa0vrg','Spring Security','290b4790-1def-4584-9b04-e9a934cad123','d6b31b9a-e1e6-4844-b909-e92c083959ce'),('ec61015c-82d4-4969-a133-fc1e4ad45fc2','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','w-9ZTeO7q_E','Multidimensional Arrays','190a2f3c-0ce8-4243-9444-f8520095576a','e74d8b9e-3bdc-4fdc-bbe3-0765c127af5e'),('eea440b9-d9ce-4bbb-afee-f231a8562798','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','JUOyKSZScW0','Bubble Sort','02249be3-12cb-45d6-8894-b78a6f317748','db9afdf2-2f19-4e58-ae0e-3f71cb5b9204'),('f47de03e-1c61-4498-a83a-00d7228b78de','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','rfscVS0vtbw','Lecture 2','fdb62d51-1b31-4f7d-b813-2ae0e2924f2c','facc7419-54c0-4d6a-9f63-5d7e21bd2390'),('f5773177-093c-40b1-9e70-bc6954d4df27','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','rfscVS0vtbw','Lecture 1','60420785-7678-4988-aa3a-70da62148aba','58852fed-9478-47f8-be10-213769d77d18'),('fd5c01a4-c7b0-4560-bd8d-e132db433239','Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec volutpat volutpat dui id aliquam. Donec sit amet tincidunt nunc. Nam ullamcorper velit.','OK_JCtrrv-c','Lecture 2','3fdb5b3e-3545-4158-9e54-f31ea34c48f1','763a44c1-3ba7-4ec8-99a0-58a4f8d4ba64');
/*!40000 ALTER TABLE `lectures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topics`
--

DROP TABLE IF EXISTS `topics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `topics` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7tuhnscjpohbffmp7btit1uff` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topics`
--

LOCK TABLES `topics` WRITE;
/*!40000 ALTER TABLE `topics` DISABLE KEYS */;
INSERT INTO `topics` VALUES ('381766bd-60e0-487f-8a0d-4105cbf06804','C#'),('842f38af-fbf9-43bb-8ce2-cfd59543c071','C++'),('1b27a86d-aa32-4663-8fa1-fba300724cec','Java'),('ccada0f9-6dc3-4b19-8232-64366c153e89','JavaScript'),('034c0db1-bbf0-4db4-b096-dc4a8d91cc8a','Php'),('4a550ebb-7f63-4b5a-b209-a38b92fce534','Python');
/*!40000 ALTER TABLE `topics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `profile_picture` varchar(255) DEFAULT NULL,
  `registration_date` datetime(6) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `teacher_request` bit(1) DEFAULT NULL,
  `username` varchar(30) NOT NULL,
  `about_me_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`),
  KEY `FKmpxdd6lmx6l7jesqpvc1afer7` (`about_me_id`),
  CONSTRAINT `FKmpxdd6lmx6l7jesqpvc1afer7` FOREIGN KEY (`about_me_id`) REFERENCES `about_me` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('1c746291-f197-4ae6-bb85-81532774360f','ivansimeonov@gmail.com','Ivan ','Simeonov','$2a$10$SgeIv9Mp9dDVk5D.DEdyXuDJyVJcShPGvPlRq1PrOj5Ev7BT8TBBq','http://res.cloudinary.com/devuni/image/upload/v1598254092/jidewshd90xeuavs7b55.jpg','2020-08-24 10:14:51.000000',_binary '',_binary '\0','ivansimeonov','fb47b1d1-62b1-4114-994b-6b2bcabec631'),('3da80e3e-d8df-434e-a013-9143f9a21e8c','venci@gmail.com','Vencislav','Ivanov','$2a$10$yTqWoxjxIv1EHpD2mMM.8.J7UJIFEUCehQYJoPec5uVfj/uBrczyq','http://res.cloudinary.com/devuni/image/upload/v1598255342/mgkflatbawpnna1yw996.jpg','2020-08-24 10:16:31.000000',_binary '',_binary '\0','venci101',NULL),('3e52c42d-8fc6-4756-bf4c-e58260cccf7c','mruseva72@abv.bg','Milena','Ruseva','$2a$10$dA0dF2DnCke2ICLJVS2k.uAbrdeaOTD83wBl8w54ha4K5jyN4WjEq','http://res.cloudinary.com/devuni/image/upload/v1598254448/ivz3k7jy9dqldnrf4tvh.jpg','2020-08-24 10:15:31.000000',_binary '',_binary '\0','mruseva',NULL),('43ad28cc-56e4-4e48-80ad-132395276513','konstantin@gmail.com','Konstantin','Mitev','$2a$10$ZT9VuROs0yClqt8VCqL2UuSBOZrqLhavYuHIsepDPMuDzMswBgBAW','http://res.cloudinary.com/devuni/image/upload/v1598254238/xpapkvzzkcl5rjftoroq.jpg','2020-08-24 10:17:03.000000',_binary '',_binary '\0','konstantin','d76d586d-e438-4060-8a13-555b0cf34d59'),('5915bbec-4f94-4564-a0da-7d21e2486753','nikolarusev@gmail.com','Nikola','Rusev','$2a$10$TY8fTR4AqGSPs3OWPwxcL.gPcZYr1HM7Vkdr89j4j2FkZLwQ3h68m','http://res.cloudinary.com/devuni/image/upload/v1598263818/tag6br7rppqg0fn4s1xm.jpg','2020-08-24 10:18:59.836785',_binary '',_binary '\0','nikola',NULL),('60a64687-7e05-40ee-b34f-4dd9469667dd','zhenyaryseva@gmail.com','Zhenya','Ruseva','$2a$10$ukCBQ7SJz6QocSVmhvkOjeOOtUfgIityEDt.klk6Cn4chB.GS2smm','http://res.cloudinary.com/devuni/image/upload/v1598254430/beo5jw3zfipwlvl00xki.jpg','2020-08-24 10:15:52.000000',_binary '',_binary '','zhenya',NULL),('83bee6ab-228d-4e25-bf00-3c9c315f8cc6','mitkorusev@gmail.com','Mitko','Rusev','$2a$10$EkaH3GsO5Joz1YHw7Fk/u.tRgXmCOrWtE6wZt9uTwop0empsnZ2Jm','http://res.cloudinary.com/devuni/image/upload/v1598254302/de3a0gr5x8xuzurphmrb.jpg','2020-08-24 10:16:11.000000',_binary '',_binary '\0','mitkorusev','2aca96e5-930a-4576-8f42-aec5cdaa88c3'),('cac45bbd-de5d-4974-8eed-397feb0292e0','qnichka98@gmail.com','Qnichka','Ivanova','$2a$10$yPkfu71hoHtBg4Iu3yfKCOdfCvaCPJnFggxH8hAMgNOCDF1fNglyK','http://res.cloudinary.com/devuni/image/upload/v1598254414/sp57sem99vnigxfjki3i.jpg','2020-08-24 10:15:12.000000',_binary '',_binary '','qnichka98',NULL),('ced4690d-7718-4f3a-b9f6-5cd398f54a19','radorusevcrypto@gmail.com','Radoslav','Rusev','$2a$10$GCRpqFGmSEeIaOUbDHxhuexuq1eROvl9QxTjWl8odpB1AI4V4OTdu','http://res.cloudinary.com/devuni/image/upload/v1598254051/vszycdaq1ieo6ul6kec0.jpg','2020-08-24 10:14:30.000000',_binary '',_binary '\0','rusevrado',NULL),('e51f20a6-aec5-43b7-82ab-f09a21ef9a33','root_admin@gmail.com','Root','Admin','$2a$10$9LF99PjTeLijcAjOzui2S.ReY3lOJo0pxhkOJiwSsoFeacPEZAv46','http://res.cloudinary.com/devuni/image/upload/v1598254029/ryex6wl2ypmhthjva2yj.jpg','2020-08-24 10:13:41.000000',_binary '',_binary '\0','root_admin',NULL),('e72f1a4b-8506-451e-a7ed-4406544cd134','admin@gmail.com','Admin','Admin','$2a$10$SwbRlaVvyWTs9jFi9Eps7./cH8y4/hsW8VDaLwRdtyTyHFmfrJ2Hm','http://res.cloudinary.com/devuni/image/upload/v1598254006/erc81drszhtzdgwzabcx.jpg','2020-08-24 10:13:58.000000',_binary '',_binary '\0','admin',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_authorities`
--

DROP TABLE IF EXISTS `users_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users_authorities` (
  `user_id` varchar(255) NOT NULL,
  `authorities_id` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`authorities_id`),
  KEY `FKmfxncv8ke1jjgna64c8kclry5` (`authorities_id`),
  CONSTRAINT `FKmfxncv8ke1jjgna64c8kclry5` FOREIGN KEY (`authorities_id`) REFERENCES `authorities` (`id`),
  CONSTRAINT `FKq3lq694rr66e6kpo2h84ad92q` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_authorities`
--

LOCK TABLES `users_authorities` WRITE;
/*!40000 ALTER TABLE `users_authorities` DISABLE KEYS */;
INSERT INTO `users_authorities` VALUES ('5915bbec-4f94-4564-a0da-7d21e2486753','7a9e9a8e-a94c-4ce9-af0e-fbeb8a1fbaf4'),('ced4690d-7718-4f3a-b9f6-5cd398f54a19','7a9e9a8e-a94c-4ce9-af0e-fbeb8a1fbaf4'),('e72f1a4b-8506-451e-a7ed-4406544cd134','7a9e9a8e-a94c-4ce9-af0e-fbeb8a1fbaf4'),('3da80e3e-d8df-434e-a013-9143f9a21e8c','964b28b1-f093-4b8e-ab68-c39ef412030f'),('3e52c42d-8fc6-4756-bf4c-e58260cccf7c','964b28b1-f093-4b8e-ab68-c39ef412030f'),('60a64687-7e05-40ee-b34f-4dd9469667dd','964b28b1-f093-4b8e-ab68-c39ef412030f'),('cac45bbd-de5d-4974-8eed-397feb0292e0','964b28b1-f093-4b8e-ab68-c39ef412030f'),('1c746291-f197-4ae6-bb85-81532774360f','ad885761-7304-4cd9-9e06-0ca82fa1c71d'),('43ad28cc-56e4-4e48-80ad-132395276513','ad885761-7304-4cd9-9e06-0ca82fa1c71d'),('83bee6ab-228d-4e25-bf00-3c9c315f8cc6','ad885761-7304-4cd9-9e06-0ca82fa1c71d'),('e51f20a6-aec5-43b7-82ab-f09a21ef9a33','ff2540ad-4dc9-4421-a49e-38aab5389c5b');
/*!40000 ALTER TABLE `users_authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_completed_courses`
--

DROP TABLE IF EXISTS `users_completed_courses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users_completed_courses` (
  `graduated_students_id` varchar(255) NOT NULL,
  `completed_courses_id` varchar(255) NOT NULL,
  PRIMARY KEY (`graduated_students_id`,`completed_courses_id`),
  KEY `FKoitepjwe5feq95mw61qwm0q2q` (`completed_courses_id`),
  CONSTRAINT `FKoitepjwe5feq95mw61qwm0q2q` FOREIGN KEY (`completed_courses_id`) REFERENCES `courses` (`id`),
  CONSTRAINT `FKq6iguslmxwj5skskyq8r9v164` FOREIGN KEY (`graduated_students_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_completed_courses`
--

LOCK TABLES `users_completed_courses` WRITE;
/*!40000 ALTER TABLE `users_completed_courses` DISABLE KEYS */;
INSERT INTO `users_completed_courses` VALUES ('ced4690d-7718-4f3a-b9f6-5cd398f54a19','1080dbe4-c0b3-4d3c-bc7b-ab7408f0f4ec'),('ced4690d-7718-4f3a-b9f6-5cd398f54a19','190a2f3c-0ce8-4243-9444-f8520095576a'),('ced4690d-7718-4f3a-b9f6-5cd398f54a19','3fdb5b3e-3545-4158-9e54-f31ea34c48f1'),('ced4690d-7718-4f3a-b9f6-5cd398f54a19','41072a72-bacd-455b-98a8-bca7d24e3772'),('ced4690d-7718-4f3a-b9f6-5cd398f54a19','c63afea2-a503-4c3f-8093-f7d1c1d83e99');
/*!40000 ALTER TABLE `users_completed_courses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'devuni_db'
--

--
-- Dumping routines for database 'devuni_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-24 17:02:57
