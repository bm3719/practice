-- event 3 rollback
-- this removes all db entries made for event 3.

-- remove npcs
delete from npc where npcid in (190000, 190001, 190002, 190003, 190004, 190005, 190006, 190007, 190008, 190009, 190010, 190011, 190012, 190013, 190014);

-- remove mobskills
delete from mobskill where mobid in (190000, 190001, 190002, 190003, 190004, 190005, 190006, 190007, 190008, 190009, 190010, 190011, 190012, 190013, 190014);

-- remove npcchat
delete from npcchat where npc_id in (190000, 190001, 190002, 190003, 190004, 190005, 190006, 190007, 190008, 190009, 190010, 190011, 190012, 190013, 190014);

-- remove droplist 
delete from droplist where mobid in (190000, 190001, 190002, 190003, 190004, 190005, 190006, 190007, 190008, 190009, 190010, 190011, 190012, 190013, 190014);

-- remove mobgroups
delete from mobgroup where id = 90;

-- remove shop additions
delete from shop where item_id = 50002;
delete from shop where item_id = 50000 and npc_id = 70014;
delete from shop where item_id = 50003;

-- remove spawns
delete from spawnlist where id > 811500420;
delete from spawnlist_boss where id = 150;

-- return all normal spawn to TI
INSERT INTO `spawnlist` VALUES ('62000','Werewolf','60','45024','0','32632','32842','0','0','32559','32799','32706','32885','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62001','Werewolf','140','45024','0','32416','33028','0','0','32305','32863','32527','33193','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62002','Werewolf','100','45024','0','32628','33128','0','0','32527','33030','32730','33227','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62003','Lycanthrope','20','45173','0','32633','32840','0','0','32560','32796','32707','32884','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62004','Lycanthrope','40','45173','0','32415','33028','0','0','32303','32862','32527','33194','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62005','Lycanthrope','40','45173','0','32628','33128','0','0','32527','33030','32730','33226','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62006','Goblin','30','45008','0','32634','32840','0','0','32560','32796','32708','32885','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62007','Goblin','30','45008','0','32509','32823','0','0','32465','32789','32553','32858','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62008','Goblin','100','45008','0','32415','33028','0','0','32304','32862','32527','33194','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62009','Goblin','60','45008','0','32628','33128','0','0','32527','33030','32730','33226','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62010','Hobgoblin','10','45140','0','32633','32840','0','0','32560','32796','32706','32884','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62011','Hobgoblin','10','45140','0','32633','32840','0','0','32466','32784','32552','32857','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62012','Hobgoblin','40','45140','0','32416','33026','0','0','32305','32862','32527','33191','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62013','Hobgoblin','40','45140','0','32629','33128','0','0','32528','33031','32731','33225','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62014','Orc Fighter','30','45082','0','32634','32840','0','0','32561','32796','32707','32884','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62015','Orc Fighter','16','45082','0','32508','32819','0','0','32465','32782','32551','32857','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62016','Orc Fighter','60','45082','0','32415','33026','0','0','32304','32860','32527','33193','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62017','Orc Fighter','40','45082','0','32629','33126','0','0','32528','33028','32730','33224','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62018','Orc','60','45009','0','32634','32841','0','0','32561','32796','32708','32886','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62019','Orc','30','45009','0','32508','32819','0','0','32464','32781','32552','32858','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62020','Orc','100','45009','0','32415','32963','0','0','32305','32863','32526','33063','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62021','Orc','60','45009','0','32628','33126','0','0','32526','33030','32730','33223','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62022','Orc Archer','20','45019','0','32633','32840','0','0','32561','32797','32705','32884','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62023','Orc Archer','10','45019','0','32507','32819','0','0','32464','32782','32551','32856','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62024','Orc Archer','60','45019','0','32415','32963','0','0','32304','32863','32526','33064','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62025','Orc Archer','60','45019','0','32628','33126','0','0','32527','33029','32730','33224','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62026','Kobold','20','45016','0','32633','32840','0','0','32560','32796','32706','32884','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62027','Kobold','10','45016','0','32508','32819','0','0','32465','32782','32552','32856','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62028','Kobold','60','45016','0','32415','32963','0','0','32304','32863','32526','33063','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62029','Kobold','60','45016','0','32628','33126','0','0','32528','33030','32729','33223','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62030','Dwarf','20','45041','0','32634','32840','0','0','32561','32796','32708','32884','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62031','Dwarf','10','45041','0','32508','32819','0','0','32465','32782','32551','32857','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62032','Dwarf','40','45041','0','32416','32964','0','0','32306','32865','32526','33064','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62033','Dwarf','40','45041','0','32628','33126','0','0','32528','33031','32728','33222','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62034','Dwarf Warrior','16','45092','0','32633','32839','0','0','32562','32796','32705','32883','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62035','Gnoll','20','45079','0','32634','32840','0','0','32562','32797','32707','32883','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62036','Gnoll','20','45079','0','32508','32819','0','0','32465','32782','32552','32856','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62037','Gnoll','60','45079','0','32415','32963','0','0','32306','32863','32525','33064','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62038','Gnoll','60','45079','0','32628','33127','0','0','32528','33030','32729','33224','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62039','Zombie','16','45065','0','32508','32820','0','0','32464','32782','32553','32859','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62040','Zombie','60','45065','0','32416','33027','0','0','32305','32863','32527','33192','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62041','Zombie','60','45065','0','32629','33127','0','0','32527','33031','32731','33224','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62042','Ghoul','10','45157','0','32508','32818','0','0','32465','32781','32552','32856','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62043','Ghoul','40','45157','0','32416','33027','0','0','32305','32863','32528','33191','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62044','Ghoul','40','45157','0','32416','33027','0','0','32529','33031','32731','33224','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62045','Skeleton','40','45107','0','32417','33128','0','0','32306','33066','32528','33190','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62046','Arachnevil','40','45136','0','32417','33128','0','0','32306','32964','32457','33190','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62047','Arachnevil','40','45136','0','32381','33077','0','0','32460','33034','32729','33221','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62048','Arachnevil Elder','30','45184','0','32382','33077','0','0','32307','32965','32458','33189','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62049','Arachnevil Elder','30','45184','0','32594','33129','0','0','32460','33035','32728','33224','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62050','Stone Golem','16','45126','0','32633','32841','0','0','32562','32798','32705','32884','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62051','Stone Golem','10','45126','0','32510','32821','0','0','32468','32786','32552','32857','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62052','Stone Golem','20','45126','0','32382','33030','0','0','32305','32966','32459','33095','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62053','Stone Golem','20','45126','0','32629','33129','0','0','32528','33033','32730','33225','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62054','Floating Eye','20','45068','0','32635','32839','0','0','32562','32797','32708','32882','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62055','Floating Eye','10','45068','0','32509','32818','0','0','32465','32781','32554','32856','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62056','Floating Eye','20','45068','0','32509','32818','0','0','32305','32966','32458','33093','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62057','Floating Eye','20','45068','0','32594','33130','0','0','32460','33037','32729','33223','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62058','Elder','10','45215','7','32381','33076','0','0','32305','32964','32458','33188','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62059','Elder','16','45215','7','32594','33130','0','0','32459','33037','32730','33224','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62060','Shepherd','16','45034','0','32416','32964','0','0','32304','32863','32528','33065','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62061','Doberman','10','45042','0','32415','33014','0','0','32303','32962','32527','33067','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62062','Beagle','10','45046','0','32394','33030','0','0','32306','32965','32482','33095','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62073','Slime','10','45060','0','32635','32843','0','0','32565','32801','32706','32885','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62074','Slime','2','45060','0','32635','32843','0','0','32606','32903','32621','32977','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62075','Slime','2','45060','0','32636','32950','0','0','32624','32937','32648','32964','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62076','Slime','5','45060','0','32508','32821','0','0','32464','32784','32553','32858','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62077','Slime','30','45060','0','32425','33042','0','0','32304','32864','32546','33221','3','60','120','0','0','0','0','0','1','8','2','100');
INSERT INTO `spawnlist` VALUES ('62078','Slime','20','45060','0','32637','33131','0','0','32547','33037','32728','33225','3','60','120','0','0','0','0','0','1','8','2','100');

-- re-add kurtz spawn
INSERT INTO `spawnlist_boss` VALUES ('38', 'Kurtz', 'Night', '1', '45600', '13', '32635', '32959', '0', '0', '32621', '32951', '32640', '32971', '0', '0', '1', '35', '0', '0', '100');
