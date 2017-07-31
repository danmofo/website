CREATE SCHEMA IF NOT EXISTS `dmoffat.com`;

CREATE TABLE IF NOT EXISTS `dmoffat.com`.author (
    id INT(11) NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    created DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
)
ENGINE = InnoDB
CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `dmoffat.com`.post (
  id INT(11) NOT NULL AUTO_INCREMENT,
  author_id INT(11),
  title VARCHAR(255),
  content TEXT,
  original_content TEXT,
  html_content TEXT,
  updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  posted_on DATETIME DEFAULT CURRENT_TIMESTAMP,
  published TINYINT(1),
  created DATETIME DEFAULT CURRENT_TIMESTAMP,
  permalink VARCHAR(255) NOT NULL,
  archived TINYINT(1) DEFAULT 1,
  PRIMARY KEY (id),
  UNIQUE INDEX permalink_UNIQUE (permalink ASC),
  CONSTRAINT fk_author
      FOREIGN KEY (author_id)
      REFERENCES `dmoffat.com`.author (id)
)
ENGINE = InnoDB
CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `dmoffat.com`.post_comment (
  id INT(11) NOT NULL AUTO_INCREMENT,
  post_id INT(11) NOT NULL,
  name VARCHAR(255) NOT NULL DEFAULT 'Anonymous',
  content TEXT,
  updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  created DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  CONSTRAINT fk_post_comment_post
    FOREIGN KEY (post_id)
    REFERENCES `dmoffat.com`.post (id))
ENGINE = InnoDB
CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `dmoffat.com`.tag (
  id INT(11) NOT NULL AUTO_INCREMENT,
  value VARCHAR(255) NOT NULL,
  updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  created DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE INDEX value_UNIQUE (value ASC))
ENGINE = InnoDB
CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `dmoffat.com`.post_tag (
  tag_id INT(11) NOT NULL,
  post_id INT(11) NOT NULL,
  PRIMARY KEY (tag_id, post_id),
  CONSTRAINT fk_tag_has_post_tag1
    FOREIGN KEY (tag_id)
    REFERENCES `dmoffat.com`.tag (id),
  CONSTRAINT fk_tag_has_post_post1
    FOREIGN KEY (post_id)
    REFERENCES `dmoffat.com`.post (id))
ENGINE = InnoDB
CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `dmoffat.com`.user (
  id INT(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255),
  updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  created DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id))
ENGINE = InnoDB
CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `dmoffat.com`.post_content_revision (
  id INT(11) NOT NULL AUTO_INCREMENT,
  post_id INT(11) NOT NULL,
  patch TEXT,
  modified DATETIME,
  PRIMARY KEY (id),
  CONSTRAINT fk_post_revision_post1
    FOREIGN KEY (post_id)
    REFERENCES `dmoffat.com`.post (id))
ENGINE = InnoDB
CHARACTER SET = utf8;

insert into tag (value) values ('next generation');
insert into tag (value) values ('stable');
insert into tag (value) values ('artificial intelligence');
insert into tag (value) values ('Virtual');
insert into tag (value) values ('conglomeration');
insert into tag (value) values ('interface');
insert into tag (value) values ('hybrid');
insert into tag (value) values ('homogeneous');
insert into tag (value) values ('exuding');
insert into tag (value) values ('dynamic');
insert into tag (value) values ('Open-architected');
insert into tag (value) values ('Polarised');
insert into tag (value) values ('Operative');
insert into tag (value) values ('mission-critical');
insert into tag (value) values ('bandwidth-monitored');
insert into tag (value) values ('empowering');
insert into tag (value) values ('knowledge base');
insert into tag (value) values ('Persistent');
insert into tag (value) values ('foreground');
insert into tag (value) values ('alliance');
insert into tag (value) values ('encoding');
insert into tag (value) values ('Cross-platform');
insert into tag (value) values ('matrices');
insert into tag (value) values ('knowledge user');
insert into tag (value) values ('Triple-buffered');
insert into tag (value) values ('toolset');
insert into tag (value) values ('regional');
insert into tag (value) values ('Customer-focused');
insert into tag (value) values ('maximized');
insert into tag (value) values ('array');
insert into tag (value) values ('benchmark');
insert into tag (value) values ('Multi-channelled');
insert into tag (value) values ('Mandatory');
insert into tag (value) values ('Robust');
insert into tag (value) values ('Implemented');
insert into tag (value) values ('definition');
insert into tag (value) values ('attitude');
insert into tag (value) values ('core');
insert into tag (value) values ('synergy');
insert into tag (value) values ('Cloned');
insert into tag (value) values ('Reactive');
insert into tag (value) values ('model');
insert into tag (value) values ('methodical');
insert into tag (value) values ('open architecture');
insert into tag (value) values ('local');
insert into tag (value) values ('Distributed');
insert into tag (value) values ('capacity');
insert into tag (value) values ('Pre-emptive');
insert into tag (value) values ('Monitored');
insert into tag (value) values ('Persevering');
insert into tag (value) values ('reciprocal');
insert into tag (value) values ('encompassing');
insert into tag (value) values ('data-warehouse');
insert into tag (value) values ('hub');
insert into tag (value) values ('Balanced');
insert into tag (value) values ('demand-driven');
insert into tag (value) values ('3rd generation');
insert into tag (value) values ('Adaptive');
insert into tag (value) values ('modular');
insert into tag (value) values ('actuating');
insert into tag (value) values ('Proactive');
insert into tag (value) values ('Versatile');
insert into tag (value) values ('website');
insert into tag (value) values ('content-based');
insert into tag (value) values ('capability');
insert into tag (value) values ('internet solution');
insert into tag (value) values ('multimedia');
insert into tag (value) values ('protocol');
insert into tag (value) values ('support');
insert into tag (value) values ('project');
insert into tag (value) values ('task-force');
insert into tag (value) values ('web-enabled');
insert into tag (value) values ('5th generation');
insert into tag (value) values ('dedicated');
insert into tag (value) values ('policy');
insert into tag (value) values ('bottom-line');
insert into tag (value) values ('Open-source');
insert into tag (value) values ('secondary');
insert into tag (value) values ('client-driven');
insert into tag (value) values ('Self-enabling');
insert into tag (value) values ('Secured');
insert into tag (value) values ('Horizontal');
insert into tag (value) values ('leading edge');
insert into tag (value) values ('concept');
insert into tag (value) values ('User-friendly');
insert into tag (value) values ('Right-sized');
insert into tag (value) values ('global');
insert into tag (value) values ('optimizing');
insert into tag (value) values ('Future-proofed');
insert into tag (value) values ('clear-thinking');
insert into tag (value) values ('service-desk');
insert into tag (value) values ('firmware');
insert into tag (value) values ('Graphic Interface');
insert into tag (value) values ('Stand-alone');
insert into tag (value) values ('asymmetric');
insert into tag (value) values ('Organic');
insert into tag (value) values ('Compatible');
insert into tag (value) values ('collaboration');
insert into tag (value) values ('Profound');
insert into tag (value) values ('Extended');
insert into tag (value) values ('Sharable');
insert into tag (value) values ('migration');
insert into tag (value) values ('monitoring');
insert into tag (value) values ('middleware');
insert into tag (value) values ('success');
insert into tag (value) values ('motivating');
insert into tag (value) values ('paradigm');
insert into tag (value) values ('needs-based');
insert into tag (value) values ('object-oriented');
insert into tag (value) values ('Face to face');
insert into tag (value) values ('flexibility');
insert into tag (value) values ('interactive');
insert into tag (value) values ('Visionary');
insert into tag (value) values ('productivity');
insert into tag (value) values ('zero tolerance');
insert into tag (value) values ('upward-trending');
insert into tag (value) values ('optimal');
insert into tag (value) values ('focus group');
insert into tag (value) values ('impactful');
insert into tag (value) values ('multi-state');
insert into tag (value) values ('Automated');
insert into tag (value) values ('Customizable');
insert into tag (value) values ('grid-enabled');
insert into tag (value) values ('user-facing');
insert into tag (value) values ('Reverse-engineered');
insert into tag (value) values ('Intuitive');
insert into tag (value) values ('projection');
insert into tag (value) values ('transitional');
insert into tag (value) values ('application');
insert into tag (value) values ('throughput');
insert into tag (value) values ('architecture');
insert into tag (value) values ('Synergistic');
insert into tag (value) values ('multi-tasking');
insert into tag (value) values ('ability');
insert into tag (value) values ('Enhanced');
insert into tag (value) values ('structure');
insert into tag (value) values ('background');
insert into tag (value) values ('Progressive');
insert into tag (value) values ('tangible');
insert into tag (value) values ('budgetary management');
insert into tag (value) values ('access');
insert into tag (value) values ('radical');
insert into tag (value) values ('well-modulated');
insert into tag (value) values ('approach');
insert into tag (value) values ('info-mediaries');
insert into tag (value) values ('Networked');
insert into tag (value) values ('Re-engineered');
insert into tag (value) values ('extranet');
insert into tag (value) values ('Organized');
insert into tag (value) values ('human-resource');
insert into tag (value) values ('systemic');
insert into tag (value) values ('adapter');
insert into tag (value) values ('fresh-thinking');
insert into tag (value) values ('Synergized');
insert into tag (value) values ('zero administration');
insert into tag (value) values ('groupware');
insert into tag (value) values ('24 hour');
insert into tag (value) values ('Diverse');
insert into tag (value) values ('executive');
insert into tag (value) values ('open system');
insert into tag (value) values ('value-added');
insert into tag (value) values ('Reduced');
insert into tag (value) values ('algorithm');
insert into tag (value) values ('Multi-layered');
insert into tag (value) values ('fault-tolerant');
insert into tag (value) values ('Focused');
insert into tag (value) values ('orchestration');
insert into tag (value) values ('system engine');
insert into tag (value) values ('time-frame');
insert into tag (value) values ('parallelism');
insert into tag (value) values ('pricing structure');
insert into tag (value) values ('Up-sized');
insert into tag (value) values ('Assimilated');
insert into tag (value) values ('6th generation');
insert into tag (value) values ('Cross-group');
insert into tag (value) values ('software');
insert into tag (value) values ('moratorium');
insert into tag (value) values ('instruction set');
insert into tag (value) values ('installation');
insert into tag (value) values ('Enterprise-wide');
insert into tag (value) values ('implementation');
insert into tag (value) values ('Decentralized');
insert into tag (value) values ('solution-oriented');
insert into tag (value) values ('full-range');
insert into tag (value) values ('bifurcated');
insert into tag (value) values ('directional');
insert into tag (value) values ('static');
insert into tag (value) values ('logistical');
insert into tag (value) values ('heuristic');
insert into tag (value) values ('high-level');
insert into tag (value) values ('De-engineered');
insert into tag (value) values ('holistic');
insert into tag (value) values ('neutral');
insert into tag (value) values ('local area network');
insert into tag (value) values ('Down-sized');
insert into tag (value) values ('national');
insert into tag (value) values ('Function-based');
insert into tag (value) values ('database');
insert into tag (value) values ('Quality-focused');
insert into tag (value) values ('archive');
insert into tag (value) values ('functionalities');
insert into tag (value) values ('emulation');
insert into tag (value) values ('Synchronised');
insert into tag (value) values ('frame');
insert into tag (value) values ('even-keeled');
insert into tag (value) values ('Seamless');
insert into tag (value) values ('context-sensitive');
insert into tag (value) values ('Front-line');
insert into tag (value) values ('moderator');
insert into tag (value) values ('contingency');
insert into tag (value) values ('solution');
insert into tag (value) values ('incremental');
insert into tag (value) values ('framework');
insert into tag (value) values ('disintermediate');
insert into tag (value) values ('attitude-oriented');
insert into tag (value) values ('Re-contextualized');
insert into tag (value) values ('help-desk');
insert into tag (value) values ('Total');
insert into tag (value) values ('Phased');
insert into tag (value) values ('Graphical User Interface');
insert into tag (value) values ('eco-centric');
insert into tag (value) values ('Switchable');
insert into tag (value) values ('Grass-roots');
insert into tag (value) values ('Upgradable');
insert into tag (value) values ('circuit');
insert into tag (value) values ('process improvement');
insert into tag (value) values ('Ameliorated');
insert into tag (value) values ('real-time');
insert into tag (value) values ('didactic');
insert into tag (value) values ('utilisation');
insert into tag (value) values ('analyzer');
insert into tag (value) values ('forecast');
insert into tag (value) values ('strategy');
insert into tag (value) values ('Fully-configurable');
insert into tag (value) values ('explicit');
insert into tag (value) values ('Integrated');
insert into tag (value) values ('system-worthy');
insert into tag (value) values ('4th generation');
insert into tag (value) values ('Managed');
insert into tag (value) values ('systematic');
insert into tag (value) values ('standardization');
insert into tag (value) values ('intangible');
insert into tag (value) values ('Multi-lateral');
insert into tag (value) values ('Public-key');
insert into tag (value) values ('discrete');
insert into tag (value) values ('Devolved');
insert into tag (value) values ('Exclusive');
insert into tag (value) values ('24/7');
insert into tag (value) values ('zero defect');
insert into tag (value) values ('intermediate');
insert into tag (value) values ('Ergonomic');
insert into tag (value) values ('hierarchy');
insert into tag (value) values ('scalable');
insert into tag (value) values ('Programmable');
insert into tag (value) values ('User-centric');
insert into tag (value) values ('Profit-focused');
insert into tag (value) values ('Configurable');
insert into tag (value) values ('Multi-tiered');
insert into tag (value) values ('contextually-based');
insert into tag (value) values ('client-server');
insert into tag (value) values ('responsive');
insert into tag (value) values ('Centralized');
insert into tag (value) values ('encryption');
insert into tag (value) values ('Fundamental');
insert into tag (value) values ('Advanced');
insert into tag (value) values ('function');
insert into tag (value) values ('Universal');
insert into tag (value) values ('hardware');
insert into tag (value) values ('Realigned');
insert into tag (value) values ('Team-oriented');
insert into tag (value) values ('Business-focused');
insert into tag (value) values ('Object-based');
insert into tag (value) values ('Inverse');
insert into tag (value) values ('Optional');
insert into tag (value) values ('mobile');
insert into tag (value) values ('coherent');
insert into tag (value) values ('complexity');
insert into tag (value) values ('infrastructure');
insert into tag (value) values ('Innovative');
insert into tag (value) values ('neural-net');
insert into tag (value) values ('intranet');
insert into tag (value) values ('customer loyalty');
insert into tag (value) values ('portal');
insert into tag (value) values ('workforce');
insert into tag (value) values ('Expanded');
insert into tag (value) values ('superstructure');
insert into tag (value) values ('uniform');
insert into tag (value) values ('bi-directional');
insert into tag (value) values ('matrix');
insert into tag (value) values ('Streamlined');
insert into tag (value) values ('non-volatile');
insert into tag (value) values ('methodology');
insert into tag (value) values ('composite');
insert into tag (value) values ('challenge');
insert into tag (value) values ('cohesive');
insert into tag (value) values ('Optimized');

insert into user (id, username, password) values (1, "danmofo", "$2a$10$e3Cz5/NhVGTM4zPRojW5Y.1fxPVAWEnlojyDD.mLNanVlL90qKVse");

insert into author (id, name) values (1, "Daniel Moffat");

insert into post (id, title, author_id, content, original_content, html_content, updated, posted_on, published, created, permalink, archived) values ('1', 'My first post', 1, 'The fourth version', '# Title \n Some content', '<h1>Title</h1>\n<p>Some content</p>\n', '2017-07-17 21:00:12', NULL, '0', '2017-07-17 20:58:36', 'post-1', '0');
insert into post (id, title, author_id, content, original_content, html_content, updated, posted_on, published, created, permalink, archived) values ('2', 'My second post', 1, 'The fourth version', '# Title \n Some content', '<h1>Title</h1>\n<p>Some content</p>\n', '2017-07-17 21:00:12', NULL, '0', '2017-07-17 20:58:36', 'post-2', '0');
insert into post (id, title, author_id, content, original_content, html_content, updated, posted_on, published, created, permalink, archived) values ('3', 'My third post', 1, 'The fourth version', '# Title \n Some content', '<h1>Title</h1>\n<p>Some content</p>\n', '2017-07-17 21:00:12', NULL, '0', '2017-07-17 20:58:36', 'post-3', '0');
insert into post (id, title, author_id, content, original_content, html_content, updated, posted_on, published, created, permalink, archived) values ('4', 'My fourth post', 1, 'The fourth version', '# Title \n Some content', '<h1>Title</h1>\n<p>Some content</p>\n', '2017-07-17 21:00:12', NULL, '0', '2017-07-17 20:58:36', 'post-4', '0');
insert into post (id, title, author_id, content, original_content, html_content, updated, posted_on, published, created, permalink, archived) values ('5', 'My fifth post', 1, 'The fourth version', '# Title \n Some content', '<h1>Title</h1>\n<p>Some content</p>\n', '2017-07-17 21:00:12', NULL, '0', '2017-07-17 20:58:36', 'post-5', '0');
insert into post (id, title, author_id, content, original_content, html_content, updated, posted_on, published, created, permalink, archived) values ('6', 'My sixth post', 1, 'The fourth version', '# Title \n Some content', '<h1>Title</h1>\n<p>Some content</p>\n', '2017-07-17 21:00:12', NULL, '0', '2017-07-17 20:58:36', 'post-6', '0');
insert into post (id, title, author_id, content, original_content, html_content, updated, posted_on, published, created, permalink, archived) values ('7', 'My seventh post', 1, 'The fourth version', '# Title \n Some content', '<h1>Title</h1>\n<p>Some content</p>\n', '2017-07-17 21:00:12', NULL, '0', '2017-07-17 20:58:36', 'post-7', '0');
insert into post (id, title, author_id, content, original_content, html_content, updated, posted_on, published, created, permalink, archived) values ('8', 'My eighth post', 1, 'The fourth version', '# Title \n Some content', '<h1>Title</h1>\n<p>Some content</p>\n', '2017-07-17 21:00:12', NULL, '0', '2017-07-17 20:58:36', 'post-8', '0');
insert into post (id, title, author_id, content, original_content, html_content, updated, posted_on, published, created, permalink, archived) values ('9', 'My ninth post', 1, 'The fourth version', '# Title \n Some content', '<h1>Title</h1>\n<p>Some content</p>\n', '2017-07-17 21:00:12', NULL, '0', '2017-07-17 20:58:36', 'post-9', '0');
insert into post (id, title, author_id, content, original_content, html_content, updated, posted_on, published, created, permalink, archived) values ('10', 'My tenth post', 1, 'The fourth version', '# Title \n Some content', '<h1>Title</h1>\n<p>Some content</p>\n', '2017-07-17 21:00:12', NULL, '0', '2017-07-17 20:58:36', 'post-10', '0');
insert into post (id, title, author_id, content, original_content, html_content, updated, posted_on, published, created, permalink, archived) values ('11', 'My eleventh post', 1, 'The fourth version', '# Title \n Some content', '<h1>Title</h1>\n<p>Some content</p>\n', '2017-07-17 21:00:12', NULL, '0', '2017-07-17 20:58:36', 'post-11', '0');

insert into post_comment (id, post_id, name, content) values (1, 1, 'Anonymous', 'This sucks bro.');
insert into post_comment (id, post_id, name, content) values (2, 1, 'Anonymous', 'Dont worry I think its ok');
insert into post_comment (id, post_id, name, content) values (3, 1, 'Anonymous', 'Nah it really sucks.');
insert into post_comment (id, post_id, name, content) values (4, 1, 'Anonymous', 'Have you seen Peep Show?');

insert into post_tag (tag_id, post_id) values (1, 1);
insert into post_tag (tag_id, post_id) values (2, 1);
insert into post_tag (tag_id, post_id) values (3, 1);
insert into post_tag (tag_id, post_id) values (4, 1);
insert into post_tag (tag_id, post_id) values (5, 1);

insert into post_content_revision (id, post_id, patch, modified) values ('1', '1', '@@ -1,22 +1,10 @@\n-# Title %0A Som\n+V\n e\n- c\n+rsi\n on\n-tent\n+ 2!\n', '2017-07-17 20:59:59');
insert into post_content_revision (id, post_id, patch, modified) values ('2', '1', '@@ -1,14 +1,18 @@\n-V\n+The fourth v\n ersion\n- three!\n', '2017-07-17 21:00:12');
