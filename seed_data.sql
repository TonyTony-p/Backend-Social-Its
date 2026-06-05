-- ============================================================
-- SEED DATA: ITSocial - Utenti Fake
-- 30 Studenti + 10 Docenti + 10 Classi Corso
-- Password per tutti: Password1
-- BCrypt hash: $2a$10$OQI8JpVyC3OyIUeiVs8BKe0VFm9rwLGKBMUe5JXVwOXyLUGOhVhFu
-- ============================================================
-- Ruoli presenti nel DB:
--   id=1      → 'User'       (studenti)
--   id=30004  → 'PROFESSORE' (docenti)
-- ============================================================

SET @pwd = '$2a$10$OQI8JpVyC3OyIUeiVs8BKe0VFm9rwLGKBMUe5JXVwOXyLUGOhVhFu';
SET @now = NOW();
SET @studente_id   = (SELECT id FROM ruoli WHERE nome = 'User');
SET @professore_id = (SELECT id FROM ruoli WHERE nome = 'PROFESSORE');

-- ============================================================
-- 30 STUDENTI
-- ============================================================
INSERT INTO utenti (nome, cognome, email, username, password, data_nascita, bio, ruolo_id, created_at, updated_at) VALUES
('Marco',      'Rossi',       'marco.rossi@studente.its.it',       'mrossi',       @pwd, '2002-03-15', 'Studente ITS', @studente_id, @now, @now),
('Giulia',     'Bianchi',     'giulia.bianchi@studente.its.it',    'gbianchi',     @pwd, '2001-07-22', 'Studente ITS', @studente_id, @now, @now),
('Luca',       'Ferrari',     'luca.ferrari@studente.its.it',      'lferrari',     @pwd, '2003-01-10', 'Studente ITS', @studente_id, @now, @now),
('Sofia',      'Russo',       'sofia.russo@studente.its.it',       'srusso',       @pwd, '2002-09-05', 'Studente ITS', @studente_id, @now, @now),
('Alessandro', 'Esposito',    'alessandro.esposito@studente.its.it','aesposito',   @pwd, '2001-11-30', 'Studente ITS', @studente_id, @now, @now),
('Valentina',  'Colombo',     'valentina.colombo@studente.its.it', 'vcolombo',     @pwd, '2003-04-18', 'Studente ITS', @studente_id, @now, @now),
('Matteo',     'Ricci',       'matteo.ricci@studente.its.it',      'mricci',       @pwd, '2002-06-25', 'Studente ITS', @studente_id, @now, @now),
('Chiara',     'Marino',      'chiara.marino@studente.its.it',     'cmarino',      @pwd, '2001-02-14', 'Studente ITS', @studente_id, @now, @now),
('Lorenzo',    'Greco',       'lorenzo.greco@studente.its.it',     'lgreco',       @pwd, '2003-08-07', 'Studente ITS', @studente_id, @now, @now),
('Francesca',  'Bruno',       'francesca.bruno@studente.its.it',   'fbruno',       @pwd, '2002-12-20', 'Studente ITS', @studente_id, @now, @now),
('Simone',     'Gallo',       'simone.gallo@studente.its.it',      'sgallo',       @pwd, '2001-05-03', 'Studente ITS', @studente_id, @now, @now),
('Alice',      'Conti',       'alice.conti@studente.its.it',       'aconti',       @pwd, '2003-10-16', 'Studente ITS', @studente_id, @now, @now),
('Davide',     'Mancini',     'davide.mancini@studente.its.it',    'dmancini',     @pwd, '2002-07-29', 'Studente ITS', @studente_id, @now, @now),
('Sara',       'De Luca',     'sara.deluca@studente.its.it',       'sdeluca',      @pwd, '2001-03-11', 'Studente ITS', @studente_id, @now, @now),
('Federico',   'Lombardi',    'federico.lombardi@studente.its.it', 'flombardi',    @pwd, '2003-01-28', 'Studente ITS', @studente_id, @now, @now),
('Martina',    'Costa',       'martina.costa@studente.its.it',     'mcosta',       @pwd, '2002-09-13', 'Studente ITS', @studente_id, @now, @now),
('Riccardo',   'Barbieri',    'riccardo.barbieri@studente.its.it', 'rbarbieri',    @pwd, '2001-06-06', 'Studente ITS', @studente_id, @now, @now),
('Elena',      'Fontana',     'elena.fontana@studente.its.it',     'efontana',     @pwd, '2003-11-22', 'Studente ITS', @studente_id, @now, @now),
('Andrea',     'Moretti',     'andrea.moretti@studente.its.it',    'amoretti',     @pwd, '2002-04-17', 'Studente ITS', @studente_id, @now, @now),
('Laura',      'Giordano',    'laura.giordano@studente.its.it',    'lgiordano',    @pwd, '2001-08-31', 'Studente ITS', @studente_id, @now, @now),
('Tommaso',    'Coppola',     'tommaso.coppola@studente.its.it',   'tcoppola',     @pwd, '2003-02-09', 'Studente ITS', @studente_id, @now, @now),
('Alessia',    'Ferretti',    'alessia.ferretti@studente.its.it',  'aferretti',    @pwd, '2002-05-24', 'Studente ITS', @studente_id, @now, @now),
('Gabriele',   'Benedetti',   'gabriele.benedetti@studente.its.it','gbenedetti',  @pwd, '2001-10-12', 'Studente ITS', @studente_id, @now, @now),
('Claudia',    'Marchetti',   'claudia.marchetti@studente.its.it', 'cmarchetti',   @pwd, '2003-07-06', 'Studente ITS', @studente_id, @now, @now),
('Stefano',    'Pellegrini',  'stefano.pellegrini@studente.its.it','spellegrini', @pwd, '2002-01-19', 'Studente ITS', @studente_id, @now, @now),
('Elisa',      'Caruso',      'elisa.caruso@studente.its.it',      'ecaruso',      @pwd, '2001-04-27', 'Studente ITS', @studente_id, @now, @now),
('Nicola',     'Ferri',       'nicola.ferri@studente.its.it',      'nferri',       @pwd, '2003-09-14', 'Studente ITS', @studente_id, @now, @now),
('Roberta',    'Santoro',     'roberta.santoro@studente.its.it',   'rsantoro',     @pwd, '2002-03-03', 'Studente ITS', @studente_id, @now, @now),
('Diego',      'Palumbo',     'diego.palumbo@studente.its.it',     'dpalumbo',     @pwd, '2001-12-08', 'Studente ITS', @studente_id, @now, @now),
('Serena',     'Amato',       'serena.amato@studente.its.it',      'samato',       @pwd, '2003-06-21', 'Studente ITS', @studente_id, @now, @now);

-- ============================================================
-- 10 DOCENTI
-- bio       → materia insegnata
-- indirizzo → istituto ITS di appartenenza
-- ============================================================
INSERT INTO utenti (nome, cognome, email, username, password, data_nascita, bio, indirizzo, ruolo_id, created_at, updated_at) VALUES
('Antonio',  'Vitale',     'antonio.vitale@docente.its.it',    'avitale',     @pwd, '1982-05-15', 'Docente di Java',                'ITS Rossellini - Roma (RM)',              @professore_id, @now, @now),
('Maria',    'Ferrara',    'maria.ferrara@docente.its.it',     'mferrara',    @pwd, '1979-11-08', 'Docente di HTML e CSS',          'ITS Steve Jobs - Catania (CT)',           @professore_id, @now, @now),
('Giuseppe', 'Riva',       'giuseppe.riva@docente.its.it',     'griva',       @pwd, '1985-03-22', 'Docente di JavaScript',          'ITS ICT Liguria - Genova (GE)',           @professore_id, @now, @now),
('Paola',    'Serra',      'paola.serra@docente.its.it',       'pserra',      @pwd, '1981-07-14', 'Docente di Python',              'ITS ICT - Torino (TO)',                   @professore_id, @now, @now),
('Roberto',  'Gentile',    'roberto.gentile@docente.its.it',   'rgentile',    @pwd, '1978-09-30', 'Docente di Database e SQL',      'ITS Lazio Digital - Roma (RM)',           @professore_id, @now, @now),
('Cristina', 'Fabbri',     'cristina.fabbri@docente.its.it',   'cfabbri',     @pwd, '1983-01-25', 'Docente di Networking',          'ITS Alessandro Volta - Trieste (TS)',     @professore_id, @now, @now),
('Emanuele', 'Bassi',      'emanuele.bassi@docente.its.it',    'ebassi',      @pwd, '1986-06-10', 'Docente di Android e Kotlin',    'ITS Apulia Digital Maker - Foggia (FG)', @professore_id, @now, @now),
('Irene',    'Caputo',     'irene.caputo@docente.its.it',      'icaputo',     @pwd, '1980-12-03', 'Docente di Cybersecurity',       'ITS ICT Campus - Benevento (BN)',         @professore_id, @now, @now),
('Marco',    'Silvestri',  'marco.silvestri@docente.its.it',   'msilvestri',  @pwd, '1977-04-18', 'Docente di Cloud Computing',     'ITS Rizzoli - Milano (MI)',               @professore_id, @now, @now),
('Daniela',  'Monti',      'daniela.monti@docente.its.it',     'dmonti',      @pwd, '1984-08-27', 'Docente di Angular e TypeScript','ITS Meccanica Meccatronica - Bologna (BO)', @professore_id, @now, @now);

-- ============================================================
-- 10 CLASSI CORSO (una per docente)
-- ============================================================
INSERT INTO classi_corso (nome, descrizione, codice_invito, tipo, professore_id, created_at, updated_at) VALUES
('Sviluppo Backend con Java',       'Programmazione Java, Spring Boot, REST API e microservizi',         'JAVA2025', 'PUBBLICA', (SELECT id FROM utenti WHERE username = 'avitale'),    @now, @now),
('Web Design con HTML e CSS',       'Struttura HTML5, CSS3, Flexbox, Grid e responsive design',          'HTML2025', 'PUBBLICA', (SELECT id FROM utenti WHERE username = 'mferrara'),   @now, @now),
('Sviluppo Web Frontend',           'JavaScript ES6+, DOM manipulation, fetch API e async/await',        'JS2025',   'PUBBLICA', (SELECT id FROM utenti WHERE username = 'griva'),      @now, @now),
('Programmazione Python',           'Python 3, OOP, librerie data science, automazione e scripting',    'PY2025',   'PUBBLICA', (SELECT id FROM utenti WHERE username = 'pserra'),     @now, @now),
('Basi di Dati e SQL',              'Progettazione ER, MySQL, query avanzate, stored procedure',         'SQL2025',  'PUBBLICA', (SELECT id FROM utenti WHERE username = 'rgentile'),   @now, @now),
('Reti e Sistemi Informatici',      'Protocolli TCP/IP, routing, firewall, VPN e sicurezza di rete',     'NET2025',  'PUBBLICA', (SELECT id FROM utenti WHERE username = 'cfabbri'),    @now, @now),
('Sviluppo Mobile Android',         'Android Studio, Kotlin, Jetpack Compose, API REST su mobile',      'MOB2025',  'PUBBLICA', (SELECT id FROM utenti WHERE username = 'ebassi'),     @now, @now),
('Cybersecurity e Sicurezza IT',    'OWASP Top 10, ethical hacking, crittografia e gestione incidenti', 'SEC2025',  'PUBBLICA', (SELECT id FROM utenti WHERE username = 'icaputo'),    @now, @now),
('Cloud Computing e DevOps',        'AWS/Azure, Docker, Kubernetes, CI/CD pipeline e infrastruttura',   'CLD2025',  'PUBBLICA', (SELECT id FROM utenti WHERE username = 'msilvestri'), @now, @now),
('Frontend Avanzato con Angular',   'Angular 17+, TypeScript, RxJS, NgRx e architettura SPA',           'ANG2025',  'PUBBLICA', (SELECT id FROM utenti WHERE username = 'dmonti'),     @now, @now);
