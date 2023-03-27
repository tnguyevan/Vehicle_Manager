DROP DATABASE IF EXISTS demo0603;
CREATE DATABASE demo0603;
USE demo0603;


DROP TABLE IF EXISTS `Role`;
CREATE TABLE `Role`(
	role_id     	TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	role_name 		ENUM ('ADMIN', 'ENDUSER') 
);

DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` ( 
	user_id 		INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_name		VARCHAR(50),
    email			VARCHAR(50),
	phone_number	VARCHAR(20) ,
	`password`		VARCHAR(800) ,
	`name`	 		NVARCHAR(50) , 
    address			NVARCHAR(150),
	`status`		TINYINT DEFAULT 0, -- 0: Not Active, 1: Active
    role_id			TINYINT UNSIGNED,
	FOREIGN KEY (role_id)  REFERENCES `Role`(role_id)
);


DROP TABLE IF EXISTS Vehicle;
CREATE TABLE Vehicle (
	id	 				INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    vehicle_name		NVARCHAR(50) NOT NULL,
	license_plates		VARCHAR(15) NOT NULL,
    vehicle_description		NVARCHAR(100) NOT NULL,
    `status`			TINYINT DEFAULT 0, -- 0: Not Active, 1: Active
	user_id				INT UNSIGNED NOT NULL,
	FOREIGN KEY (user_id) REFERENCES `User`(user_id)
);


DROP TABLE IF EXISTS Movement_history;
CREATE TABLE  Movement_history(
	id					INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    start_date			DATETIME DEFAULT NOW(),
	average_speed   	FLOAT NOT NULL,
	vehicle_id			INT UNSIGNED NOT NULL,
	FOREIGN KEY (vehicle_id) REFERENCES Vehicle(id)
);

DROP TABLE IF EXISTS Details;
CREATE TABLE Details (
	id						INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	start_position 			VARCHAR(30) NOT NULL,
	end_position			VARCHAR(30) NOT NULL,
	average_speed 			FLOAT NOT NULL,
	start_time				DATETIME DEFAULT NOW(),
    end_time				DATETIME DEFAULT NOW(),
    id_movement_history		INT UNSIGNED NOT NULL,
	FOREIGN KEY (id_movement_history) REFERENCES Movement_history(id)
);

DROP TABLE IF EXISTS system_installation;
CREATE TABLE system_installation (
	id				INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	hotline 		VARCHAR(200) NOT NULL,
	banner			VARCHAR(200) NOT NULL,
	logo 			VARCHAR(200) NOT NULL
);

-- Create table Registration_User_Token
DROP TABLE IF EXISTS 	`Registration_User_Token`;
CREATE TABLE IF NOT EXISTS `Registration_User_Token` ( 	
	id 				INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`token`	 		CHAR(36) NOT NULL UNIQUE,
	`user_id` 		SMALLINT UNSIGNED NOT NULL,
	`expiryDate` 	DATETIME NOT NULL
);

-- Create table Reset_Password_Token
DROP TABLE IF EXISTS 	`Reset_Password_Token`;
CREATE TABLE IF NOT EXISTS `Reset_Password_Token` ( 	
	id 				INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	`token`	 		CHAR(36) NOT NULL UNIQUE,
	`user_id` 		SMALLINT UNSIGNED NOT NULL,
	`expiryDate` 	DATETIME NOT NULL
);
	

INSERT INTO `Role` (role_name)                                                                                                                                           
VALUES 				('ADMIN'),
					('ENDUSER');
	

INSERT INTO `User`  (user_name,  						    email,        phone_number,       `password`,                                                             `name`,     address,    `status` , role_id)
values	            ("1900rr01",	   								NULL,     	'013423432453',	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi',			  'Văn Hanh',    'Hà Nội',		1	   ,'2'    ),	 
					("1900rr02",				 				  NULL	,		'023443342546',	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi',			'Thanh Hưng',	 'Hà Nội',		1	   ,'2'    ),	 
					('can.tuananh@vti',	  'cananh.tuan12@vti.com',		'023543523446',	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi',			  'Tuấn Anh',	 'Hà Nội',		1	   ,'1'    ),	 
					("1900rr03",				 	 				NULL,		'023443243354',	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi',			  'Anh Toàn',	 'Hà Nội',		1	   ,'2'    ),	 
					("1900rr04"			,						NULL,		'023423433535',	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi',			 'Mạnh Hùng',	 'Hà Nội',		1	   ,'2'    ),	
					('maianhvti123',		 'maianhng@gmail.com',		'023423243354',	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi',			   'Mai Anh',	 'Hà Nội',		1	   ,'1'    ),	
					('tuanvti12344',	     'tuan1234@gmail.com',		'074234234354',	'$2a$10$W2neF9.6Agi6kAKVq8q3fec5dHW8KUA.b0VSIGdIZyUravfLpyIFi',			  'Văn Tuấn',	 'Hà Nội',		1	   ,'1'    );	

																			
INSERT INTO Vehicle     	   (vehicle_name, 	   license_plates,     vehicle_description,  `status`, user_id)
values	            		    ('wave',	'12f - 01333',		'xe ddep',			'0',	  5),	 
								('vision',	'14f - 01473',		'xe nhanh',			'0',	  2),	 
								('alpha',	'15f - 01333',		'xe moi',			'0',	  1),	 
								('xe ga',	'16f - 04233',		'xe cham',			'1',	  4),	 
								('make',	'17f - 06533',		'xe mau xanh',		'0',	  5),	
								('1123',	'18f - 01123',		'xe mauf den',		'1',	  4),	
								('2324',	'19f - 01767',		'xe mau tranwg',	'1',	  2);	

	
INSERT INTO   Movement_history 			(start_date, 			average_speed,     vehicle_id)
values	            		 	("2023-03-10 07:29:00",	    	40.6,			3),	 
								("2023-03-09 08:29:00",			40.3,			2),	 
								(now(),							40.8,			4),	 
								(now(),							40.9,			6),	 
								("2023-03-06 07:29:00",			40.2,			4),	
								("2023-03-05 08:29:00",			40.5,			4),	
								("2023-02-13 09:29:00",			40.3,			6);	

INSERT INTO Details    (start_position, 	   end_position,  average_speed,  	 start_time, 	end_time,	 	 id_movement_history)
values	            		    ('40.56',	       			'40.56',		40.6,  				    now(),		  		now(),			1),	 
								('40.23',					'40.23',		40.3,					now(),		  		now(),			7),	 
                                ('42.58',					'42.58',		50.4,	"2023-03-15 07:29:00",		  	"2023-03-15 08:00:00",			4),	 
								('42.58',					'42.58',		30.5,	"2023-03-15 08:30:00",		  	"2023-03-15 09:00:00",			4),	 
								('42.58',					'42.58',		37.8,	"2023-03-15 13:30:00",		  	"2023-03-15 14:00:00",			4),	 
								('42.58',					'42.58',		45.8,	"2023-03-15 10:29:00",		  	"2023-03-15 11:00:00",			4),	 
								('41.49',					'41.49',		40.9,	 "2023-03-15 07:29:00",		  	"2023-03-15 08:00:00",			3),	 
                                ('41.49',					'41.49',		40.9,	 "2023-03-15 08:29:00",			"2023-03-15 09:00:00",			3),	
								('40.62',					'40.62',		40.2,	 "2023-03-15 09:29:00",		 	"2023-03-15 11:00:00",			3),	
								('40.56',					'40.56',		40.5,	 				now(),		  		now(),			6),	
								('42.39',					'42.39',		40.3,	 				now(),		  		now(),  		5);		

INSERT INTO system_installation    (hotline, 	 banner,  logo)
values	              ('19002345',	 'https://cdn.tgdd.vn//GameApp/-1//7-ung-dung-goi-dat-xe-cong-nghe-pho-bien-tai-viet-nam-2-800x450.jpg',
		"https://lelogama.go-jek.com/component/hero/picture/About_1_desktop.jpg");	 

              
             
