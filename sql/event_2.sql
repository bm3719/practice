-- create item for event 2
insert into etcitem values (50000, 'Personal Lubricant', 'Personal Lubricant', 'event', 'none', 'none', 1000, 1176, 3129, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0);

-- create event mops
INSERT INTO `npc` VALUES ('90000', 'Gimp', 'Gimp', 'L1Monster', '1152', '20', '120', '60', '-4', '9', '10', '14', '7', '8', '1', '26', '-20', 'small', '0', '0', '0', '1', '10', '1', '480', '1520', '1520', '1520', '0', '0', '0', '0', '0', '0', 'gimp', '1', '-1', '-1', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0', '0', '-1', '0', '0');
INSERT INTO `npc` VALUES ('90001', 'Gimp Elder', 'Gimp Elder', 'L1Monster', '1158', '27', '350', '110', '-10', '9', '11', '14', '12', '12', '30', '37', '-10', 'small', '0', '0', '0', '1', '1', '0', '960', '960', '960', '960', '0', '0', '0', '0', '0', '0', 'gimp', '1', '-1', '-1', '0', '0', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0', '0', '-1', '0', '0');
INSERT INTO `npc` VALUES ('90002', 'Lube Golem', 'Lube Golem', 'L1Monster', '4633', '43', '1885', '450', '-35', '19', '20', '15', '10', '10', '60', '2026', '-25', 'small', '1', '0', '0', '0', '1', '0', '960', '1280', '1400', '1200', '0', '0', '0', '0', '0', '0', 'gimp', '1', '-1', '-1', '0', '0', '1', '500', '10', '500', '5', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '0', '0', '-1', '0', '0');

-- add lube golem mobskill
insert into mobskill values (90002, 0, 'Lube Golem', 2, 20, 0, 0, -6, 0, 0, 0, 0, 0, 12, 46, 0, 0, 0, 0, 0, 0);

-- add event spawn 
insert into spawnlist values (801500397, 'mainland', 1200, 90000, 0, 33343, 32767, 959, 767, 32384, 32000, 34303, 33535, 7, 100, 200, 4, 0, 100, 0, 0);
insert into spawnlist values (801500398, 'mainland', 1000, 90001, 0, 33343, 32767, 959, 767, 32384, 32000, 34303, 33535, 7, 100, 200, 4, 0, 100, 0, 0);
insert into spawnlist values (801500399, 'mainland', 100, 90002, 0, 33343, 32767, 959, 767, 32384, 32000, 34303, 33535, 7, 100, 250, 4, 0, 100, 0, 0);

-- set lube shop price to 1k
update shop set purchasing_price = 1000 where item_id = 50000;

-- event 2.1 update

-- modify gimp npc entry
update npc set ac = -5, dex = 16, hp = 125 where npcid = 90000;
update npc set ac = -11, str = 14, hp = 360 where npcid = 90001;
update npc set ac = -36, intel = 12, hpr = 25 where npcid = 90002;

-- modify event mop count
update spawnlist set count = 1100 where id = 801500397;
update spawnlist set count = 900 where id = 801500398;
update spawnlist set count = 90 where id = 801500399;

-- remove old event mop drops
delete from droplist where mobid in (90000, 90001, 90002);

-- create event mop droplists
-- gimp
-- adena
insert into droplist values (90000, 40308, 100, 400, 1000000);
-- shadow mask
insert into droplist values (90000, 20004, 1, 1, 6500);
-- dex helm
insert into droplist values (90000, 20021, 1, 1, 6500);
-- con helm
insert into droplist values (90000, 20039, 1, 1, 5000);
-- rkh
insert into droplist values (90000, 20027, 1, 1, 9000);
-- bomp
insert into droplist values (90000, 20226, 1, 1, 6500);
-- red cloak
insert into droplist values (90000, 20065, 1, 1, 2500);
-- shadow gloves
insert into droplist values (90000, 20164, 1, 1, 5500);
-- ets
insert into droplist values (90000, 20084, 1, 1, 5500);
-- rksheild
insert into droplist values (90000, 20230, 1, 1, 6500);
-- rksword
insert into droplist values (90000, 30, 1, 1, 5500);
-- som
insert into droplist values (90000, 126, 1, 1, 4500);
-- csom
insert into droplist values (90000, 115, 1, 1, 1500);
-- sovereign's majesty cloak
insert into droplist values (90000, 20051, 1, 1, 1000);
-- shadow boots
insert into droplist values (90000, 20195, 1, 1, 1500);
-- gop
insert into droplist values (90000, 20171, 1, 1, 2500);
-- com
insert into droplist values (90000, 20055, 1, 1, 2500);
-- claw of varlok (elf)
insert into droplist values (90000, 40551, 1, 1, 1400);
-- eye of varlok (mage)
insert into droplist values (90000, 40550, 1, 1, 1400);
-- heart of varlok (prince)
insert into droplist values (90000, 40552, 1, 1, 1400);
-- sword of varlok
insert into droplist values (90000, 40549, 1, 1, 1400);
-- personal lubricant
insert into droplist values (90000, 50000, 1, 1, 350000);
-- carrot
insert into droplist values (90000, 40060, 1, 1, 350000);
-- b-banna
insert into droplist values (90000, 140062, 1, 1, 500);
-- nzel
insert into droplist values (90000, 40074, 1, 1, 34000);
-- ndai
insert into droplist values (90000, 40087, 1, 1, 10000);

-- gimp elder
-- adena
insert into droplist values (90001, 40308, 200, 300, 1000000);
-- shadow mask
insert into droplist values (90001, 20004, 1, 1, 9000);
-- dex helm
insert into droplist values (90001, 20021, 1, 1, 9000);
-- con helm
insert into droplist values (90001, 20039, 1, 1, 5000);
-- rkh
insert into droplist values (90001, 20027, 1, 1, 11000);
-- bomp
insert into droplist values (90001, 20226, 1, 1, 9000);
-- red cloak
insert into droplist values (90001, 20065, 1, 1, 6000);
-- shadow gloves
insert into droplist values (90001, 20164, 1, 1, 9000);
-- ets
insert into droplist values (90001, 20084, 1, 1, 7000);
-- rksheild
insert into droplist values (90001, 20230, 1, 1, 9000);
-- rksword
insert into droplist values (90001, 30, 1, 1, 7000);
-- som
insert into droplist values (90001, 126, 1, 1, 7000);
-- csom
insert into droplist values (90001, 115, 1, 1, 5000);
-- sovereign's majesty cloak
insert into droplist values (90001, 20051, 1, 1, 4000);
-- shadow boots
insert into droplist values (90001, 20195, 1, 1, 5000);
-- gop
insert into droplist values (90001, 20171, 1, 1,6000);
-- com
insert into droplist values (90001, 20055, 1, 1, 6000);
-- claw of varlok (elf)
insert into droplist values (90001, 40551, 1, 1, 2000);
-- eye of varlok (mage)
insert into droplist values (90001, 40550, 1, 1, 2000);
-- heart of varlok (prince)
insert into droplist values (90001, 40552, 1, 1, 2000);
-- sword of varlok
insert into droplist values (90001, 40549, 1, 1, 2000);
-- personal lubricant
insert into droplist values (90001, 50000, 1, 1, 400000);
-- carrot
insert into droplist values (90001, 40060, 1, 1, 400000);
-- b-banna
insert into droplist values (90001, 140062, 1, 1, 1000);
-- nzel
insert into droplist values (90001, 40074, 1, 1, 40000);
-- ndai
insert into droplist values (90001, 40087, 1, 1, 18000);
-- bzel
insert into droplist values (90001, 140074, 1, 1, 5000);
-- bdai
insert into droplist values (90001, 140087, 1, 1, 2000);
-- czel
insert into droplist values (90001, 240074, 1, 1, 3000);
-- cdai
insert into droplist values (90001, 240087, 1, 1, 1500);

-- lube golem
-- adena
insert into droplist values (90002, 40308, 6000, 18000, 1000000);
-- shadow mask
insert into droplist values (90002, 20004, 1, 1, 25000);
-- dex helm
insert into droplist values (90002, 20021, 1, 1, 25000);
-- con helm
insert into droplist values (90002, 20039, 1, 1, 25000);
-- rkh
insert into droplist values (90002, 20027, 1, 1, 40000);
-- bomp
insert into droplist values (90002, 20226, 1, 1, 20000);
-- red cloak
insert into droplist values (90002, 20065, 1, 1, 9000);
-- shadow gloves
insert into droplist values (90002, 20164, 1, 1, 22000);
-- ets
insert into droplist values (90002, 20084, 1, 1, 18000);
-- rksheild
insert into droplist values (90002, 20230, 1, 1, 25000);
-- rksword
insert into droplist values (90002, 30, 1, 1, 25000);
-- som
insert into droplist values (90002, 126, 1, 1, 22000);
-- csom
insert into droplist values (90002, 115, 1, 1, 10500);
-- sovereign's majesty cloak
insert into droplist values (90002, 20051, 1, 1, 9500);
-- shadow boots
insert into droplist values (90002, 20195, 1, 1, 8500);
-- gop
insert into droplist values (90002, 20171, 1, 1, 8500);
-- com
insert into droplist values (90002, 20055, 1, 1, 8500);
-- claw of varlok (elf)
insert into droplist values (90002, 40551, 1, 1, 5000);
-- eye of varlok (mage)
insert into droplist values (90002, 40550, 1, 1, 5000);
-- heart of varlok (prince)
insert into droplist values (90002, 40552, 1, 1, 5000);
-- sword of varlok
insert into droplist values (90002, 40549, 1, 1, 5000);
-- personal lubricant
insert into droplist values (90002, 50000, 10, 35, 1000000);
-- carrot
insert into droplist values (90002, 40060, 30, 90, 1000000);
-- b-banna
insert into droplist values (90002, 140062, 1, 1, 7000);
-- nzel
insert into droplist values (90002, 40074, 1, 5, 280000);
-- ndai
insert into droplist values (90002, 40087, 1, 3, 270000);
-- bzel
insert into droplist values (90002, 140074, 1, 1, 49000);
-- bdai
insert into droplist values (90002, 140087, 1, 1, 19000);
-- czel
insert into droplist values (90002, 240074, 1, 1, 30000);
-- cdai
insert into droplist values (90002, 240087, 1, 1, 10000);

-- uber event iris
-- adena
insert into droplist values (90003, 40308, 6000, 28000, 1000000);
-- shadow mask
insert into droplist values (90003, 20004, 1, 1, 75000);
-- dex helm
insert into droplist values (90003, 20021, 1, 1, 75000);
-- con helm
insert into droplist values (90003, 20039, 1, 1, 75000);
-- rkh
insert into droplist values (90003, 20027, 1, 1, 90000);
-- bomp
insert into droplist values (90003, 20226, 1, 1, 75000);
-- red cloak
insert into droplist values (90003, 20065, 1, 1, 29000);
-- shadow gloves
insert into droplist values (90003, 20164, 1, 1, 32000);
-- ets
insert into droplist values (90003, 20084, 1, 1, 38000);
-- rksheild
insert into droplist values (90003, 20230, 1, 1, 45000);
-- rksword
insert into droplist values (90003, 30, 1, 1, 55000);
-- som
insert into droplist values (90003, 126, 1, 1, 52000);
-- csom
insert into droplist values (90003, 115, 1, 1, 30500);
-- sovereign's majesty cloak
insert into droplist values (90003, 20051, 1, 1, 19500);
-- shadow boots
insert into droplist values (90003, 20195, 1, 1, 28500);
-- gop
insert into droplist values (90003, 20171, 1, 1, 28500);
-- com
insert into droplist values (90003, 20055, 1, 1, 28500);
-- claw of varlok (elf)
insert into droplist values (90003, 40551, 1, 1, 7000);
-- eye of varlok (mage)
insert into droplist values (90003, 40550, 1, 1, 7000);
-- heart of varlok (prince)
insert into droplist values (90003, 40552, 1, 1, 7000);
-- sword of varlok
insert into droplist values (90003, 40549, 1, 1, 7000);
-- personal lubricant
insert into droplist values (90003, 50000, 10, 75,  1000000);
-- carrot
insert into droplist values (90003, 40060, 30, 900, 1000000);
-- b-banna
insert into droplist values (90003, 140062, 1, 1, 17000);
-- nzel
insert into droplist values (90003, 40074, 1, 12, 500000);
-- ndai
insert into droplist values (90003, 40087, 1, 4, 500000);
-- bzel
insert into droplist values (90003, 140074, 1, 7, 400000);
-- bdai
insert into droplist values (90003, 140087, 1, 2, 400000);
-- czel
insert into droplist values (90003, 240074, 1, 2, 150000);
-- cdai
insert into droplist values (90003, 240087, 1, 1, 150000);
-- tsu
insert into droplist values (90003, 57, 1, 1, 10000);
-- s-cloak
insert into droplist values (90003, 20074, 1, 1, 10000);
--  ess:con
insert into droplist values (90003, 209, 1, 1, 25000);
-- cg
insert into droplist values (90003, 20175, 1, 1, 11000);
-- bume
insert into droplist values (90003, 450003, 1, 1, 5000);
-- thor
insert into droplist values (90003, 450000, 1, 1, 5000);
-- scorn
insert into droplist values (90003, 36, 1, 1, 5000);

-- add event iris
INSERT INTO `npc` VALUES ('90003', 'Lubricated Dominatrix', 'Lubricated Dominatrix', 'L1Monster', '3180', '65', '10500', '900', '-75', '28', '16', '14', '16', '18', '80', '3726', '-80', 'small', '0', '0', '0', '0', '1', '0', '1040', '1400', '1240', '1240', '3', '0', '0', '0', '0', '0', 'gimp', '1', '-1', '-1', '0', '0', '1', '500', '320', '500', '50', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '-1', '12', '0');

-- event iris mobskill
INSERT INTO `mobskill` VALUES ('90003', '0', 'uber event iris: tornado', '2', '20', '0', '0', '-4', '0', '0', '0', '0', '0', '40', '53', '0', '0', '0', '0', '0', '0');
INSERT INTO `mobskill` VALUES ('90003', '1', 'uber event iris: cancel', '2', '7', '0', '0', '-6', '0', '3', '0', '0', '0', '0', '44', '0', '0', '0', '0', '0', '0');
INSERT INTO `mobskill` VALUES ('90003', '2', 'uber event iris: summon', '3', '2', '0', '0', '-3', '0', '3', '0', '0', '0', '0', '0', '0', '0', '90002', '1', '2', '0');
INSERT INTO `mobskill` VALUES ('90003', '3', 'uber event iris: whip', '1', '100', '0', '0', '-3', '0', '0', '3', '0', '0', '30', '0', '0', '0', '0', '0', '0', '0');

-- add event iris spawn and friends
insert into spawnlist values (801500400, 'mainland', 1, 90003, 0, 33499, 33205, 0, 0, 0, 0, 0, 0, 5, 11000, 11001, 4, 1, 35, 1, 0);
insert into spawnlist values (801500401, 'mainland', 1, 90002, 0, 33503, 33205, 0, 0, 0, 0, 0, 0, 1, 11000, 11001, 4, 1, 35, 1, 0);
insert into spawnlist values (801500402, 'mainland', 1, 90002, 0, 33499, 33202, 0, 0, 0, 0, 0, 0, 1, 11000, 11001, 4, 1, 35, 1, 0);

-- event 2.2 update

-- modify gimp npc entry
update npc set ac = -5, dex = 16, hp = 125 where npcid = 90000;
update npc set ac = -11, str = 14, hp = 360 where npcid = 90001;
update npc set ac = -35, str = 22, intel = 12, hpr = 10 where npcid = 90002;
update npc set hpr = 100 where npcid = 90003;

-- event 2.3 update

-- decrease boss spawn
update spawnlist set min_respawn_delay = 18000, max_respawn_delay = 18000 where id in (801500400, 801500401, 801500402);

-- redo event iris drops 
delete from droplist where mobid = 90003;

-- uber event iris
-- adena
insert into droplist values (90003, 40308, 6000, 28000, 1000000);
-- shadow mask
insert into droplist values (90003, 20004, 1, 1, 95000);
-- dex helm
insert into droplist values (90003, 20021, 1, 1, 105000);
-- con helm
insert into droplist values (90003, 20039, 1, 1, 105000);
-- rkh
insert into droplist values (90003, 20027, 1, 1, 140000);
-- bomp
insert into droplist values (90003, 20226, 1, 1, 95000);
-- red cloak
insert into droplist values (90003, 20065, 1, 1, 89000);
-- shadow gloves
insert into droplist values (90003, 20164, 1, 1, 98000);
-- ets
insert into droplist values (90003, 20084, 1, 1, 83000);
-- rksheild
insert into droplist values (90003, 20230, 1, 1, 88000);
-- rksword
insert into droplist values (90003, 30, 1, 1, 88000);
-- som
insert into droplist values (90003, 126, 1, 1, 82000);
-- csom
insert into droplist values (90003, 115, 1, 1, 60500);
-- sovereign's majesty cloak
insert into droplist values (90003, 20051, 1, 1, 49500);
-- shadow boots
insert into droplist values (90003, 20195, 1, 1, 68500);
-- gop
insert into droplist values (90003, 20171, 1, 1, 78500);
-- com
insert into droplist values (90003, 20055, 1, 1, 68500);
-- claw of varlok (elf)
insert into droplist values (90003, 40551, 1, 1, 8000);
-- eye of varlok (mage)
insert into droplist values (90003, 40550, 1, 1, 8000);
-- heart of varlok (prince)
insert into droplist values (90003, 40552, 1, 1, 8000);
-- sword of varlok
insert into droplist values (90003, 40549, 1, 1, 8000);
-- personal lubricant
insert into droplist values (90003, 50000, 10, 45,  1000000);
-- carrot
insert into droplist values (90003, 40060, 30, 500, 1000000);
-- b-banna
insert into droplist values (90003, 140062, 1, 1, 37000);
-- nzel
insert into droplist values (90003, 40074, 1, 12, 500000);
-- ndai
insert into droplist values (90003, 40087, 1, 4, 500000);
-- bzel
insert into droplist values (90003, 140074, 1, 7, 400000);
-- bdai
insert into droplist values (90003, 140087, 1, 2, 400000);
-- czel
insert into droplist values (90003, 240074, 1, 2, 150000);
-- cdai
insert into droplist values (90003, 240087, 1, 1, 150000);
-- tsu
insert into droplist values (90003, 57, 1, 1, 72000);
-- dex bow
insert into droplist values (90003, 215, 1, 1, 109000);
-- s-cloak
insert into droplist values (90003, 20074, 1, 1, 72000);
-- cg
insert into droplist values (90003, 20175, 1, 1, 122000);
-- bume
insert into droplist values (90003, 450003, 1, 1, 28000);
-- thor
insert into droplist values (90003, 450000, 1, 1, 28000);
-- scorn
insert into droplist values (90003, 36, 1, 1, 28000);

-- modify iris npc entry
update npc set hpr = 75 where npcid = 90003;

-- increase lube golem spawn
update spawnlist set count = 1090 where id = 801500397;
update spawnlist set count = 900 where id = 801500398;
update spawnlist set count = 100 where id = 801500399;

