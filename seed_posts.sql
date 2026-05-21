-- ============================================================
-- SEED: Post docenti, post studenti, commenti e likes
-- ============================================================

SET @now = NOW();

-- ============================================================
-- POST DEI DOCENTI (2 per docente)
-- ============================================================

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='avitale'),
'Oggi abbiamo visto i principi SOLID della programmazione OOP. Il Single Responsibility Principle è fondamentale: ogni classe deve avere un solo motivo per cambiare. Chi riesce a spiegarmi con parole sue cosa significa? Domani esercizio pratico! 💡',
@now, @now, @now);
SET @p1 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='avitale'),
'Esercizio per la prossima lezione: implementate una REST API con Spring Boot per gestire un CRUD completo di una tabella "Studenti". Usate JPA + H2 in memoria e Postman per testare gli endpoint. Chi finisce prima pubblica il link al repo GitHub! 🚀',
@now, @now, @now);
SET @p2 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='mferrara'),
'CSS Flexbox è il vostro migliore amico per i layout moderni! Ricordate: justify-content gestisce l''asse principale, align-items quello trasversale. Qualcuno ha provato a costruire una navbar responsive solo con Flexbox? Condividete il CodePen! 🎨',
@now, @now, @now);
SET @p3 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='mferrara'),
'Domani in aula costruiamo un layout completo con CSS Grid. Preparate VS Code e studiate la differenza tra grid-template-columns e grid-template-areas. Chi arriva già preparato ottiene un bonus! 📐',
@now, @now, @now);
SET @p4 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='griva'),
'var, let o const? Non è solo una scelta stilistica! Lo scope, l''hoisting e l''immutabilità cambiano il comportamento del codice. Regola pratica: usate sempre const, poi let se dovete riassegnare, mai var. Qualcuno vuole spiegarmi perché? 🤔',
@now, @now, @now);
SET @p5 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='griva'),
'Async/Await ha reso il codice asincrono molto più leggibile rispetto alle Promises annidate. Domani vediamo come gestire errori con try/catch e come fare chiamate API parallele con Promise.all(). Preparatevi con un account su JSONPlaceholder! 🔄',
@now, @now, @now);
SET @p6 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='pserra'),
'I type hints in Python non rallentano il codice ma migliorano la leggibilità e permettono a strumenti come mypy di trovare bug prima dell''esecuzione. Da Python 3.10 la sintassi è semplicissima: str | int invece di Union[str, int]. Li usate già? 🐍',
@now, @now, @now);
SET @p7 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='pserra'),
'Esercizio per casa: leggete un file CSV con pandas, filtrate le righe dove il voto è superiore a 6, calcolate la media e generate un grafico a barre con matplotlib. Salvate tutto in un Jupyter Notebook e caricatelo su GitHub! 📊',
@now, @now, @now);
SET @p8 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='rgentile'),
'La Terza Forma Normale (3NF) è essenziale: ogni attributo non-chiave deve dipendere SOLO dalla chiave primaria. Se trovate dipendenze transitive, è ora di dividere la tabella! Avete domande sulla normalizzazione? 🗄️',
@now, @now, @now);
SET @p9 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='rgentile'),
'Avete mai usato EXPLAIN ANALYZE su una query lenta? Domani vediamo come interpretare il piano di esecuzione di MySQL, come aggiungere indici strategici e perché un JOIN mal scritto può trasformare una query da 0.01s a 30s. 🔍',
@now, @now, @now);
SET @p10 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='cfabbri'),
'Il modello OSI a 7 livelli è la base di tutto il networking. Oggi ci siamo concentrati sul livello 3 (Network). Domanda per voi: qual è la differenza principale tra un router e uno switch? Chi risponde correttamente fa lezione da casa venerdì! 🌐',
@now, @now, @now);
SET @p11 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='cfabbri'),
'Laboratorio di domani: configuriamo una rete virtuale con Cisco Packet Tracer. Assegnerete indirizzi IP statici, configurerete subnet mask e route statiche tra due reti diverse. Installate Packet Tracer se non lo avete già! 🖧',
@now, @now, @now);
SET @p12 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='ebassi'),
'Jetpack Compose ha cambiato il modo di sviluppare UI su Android. Niente più XML, tutto è codice Kotlin dichiarativo. Oggi abbiamo visto remember e mutableStateOf per gestire lo stato. Qualcuno ha già fatto un progetto personale con Compose? 📱',
@now, @now, @now);
SET @p13 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='ebassi'),
'Domani implementiamo la navigazione nella nostra app Android con Jetpack Navigation Component. Il pattern single-activity con NavHost rende il codice molto più pulito. Preparate l''app che stavate sviluppando, aggiungiamo 3 schermate collegate! 🗺️',
@now, @now, @now);
SET @p14 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='icaputo'),
'SQL Injection è ancora tra le top 3 vulnerabilità OWASP nel 2024. Oggi su un ambiente di test ho mostrato come una singola apostrofe in un form può esporre l''intero database. Difesa: sempre prepared statements o ORM, MAI concatenare input utente! 🔒',
@now, @now, @now);
SET @p15 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='icaputo'),
'CTF della settimana! Ho preparato una web app volutamente vulnerabile su un server di test. Trovate le falle, documentate il metodo di exploit e scrivete la remediation. Chi trova tutte e 5 le vulnerabilità entro venerdì... sorpresa! 🏴‍☠️',
@now, @now, @now);
SET @p16 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='msilvestri'),
'AWS vs Azure vs GCP: non esiste la scelta giusta in assoluto, dipende dal caso d''uso. Oggi abbiamo confrontato i servizi di compute: EC2, Azure VMs e Compute Engine. Per chi impara con budget limitato ricordatevi dei free tier! Quale preferite? ☁️',
@now, @now, @now);
SET @p17 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='msilvestri'),
'Domani configuriamo una pipeline CI/CD completa con GitHub Actions: build automatica, test unitari, build Docker image e deploy su server cloud. Il DevOps non è un ruolo, è una cultura! Createvi un account GitHub se non l''avete ancora. 🔄',
@now, @now, @now);
SET @p18 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='dmonti'),
'TypeScript non è "JavaScript difficile", è JavaScript con la rete di sicurezza! I tipi statici vi salvano da errori a runtime che in JS puro scoprireste solo in produzione. Oggi abbiamo visto interface vs type alias. Qualcuno ha ancora resistenze? 😄',
@now, @now, @now);
SET @p19 = LAST_INSERT_ID();

INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='dmonti'),
'Angular Signals è il nuovo sistema di reattività di Angular 17+ che semplifica la gestione dello stato rispetto a RxJS puro. Oggi confrontiamo signal() con BehaviorSubject. Chi ha già lavorato con Vue ref() o React useState troverà il concetto familiare! ⚡',
@now, @now, @now);
SET @p20 = LAST_INSERT_ID();


-- ============================================================
-- POST DEGLI STUDENTI (25 post)
-- ============================================================
INSERT INTO post (id_utente, contenuto, created_at, data_ora, updated_at) VALUES
((SELECT id FROM utenti WHERE username='mrossi'),
'Ho finalmente fatto girare il mio primo progetto Spring Boot! Ci ho messo 3 ore a capire il problema... avevo dimenticato @RestController 😂 A volte le cose più semplici fanno perdere più tempo. Voi che errori assurdi avete fatto?',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='gbianchi'),
'Qualcuno mi spiega la differenza pratica tra interface e abstract class in Java? Ho capito la teoria ma non capisco quando usare una rispetto all''altra in un progetto reale.',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='lferrari'),
'Consiglio del giorno: usate Git fin dal primo commit, non "quando il progetto è pronto". Ho perso 2 giorni di lavoro la settimana scorsa per non averlo fatto. Lezione imparata nel modo più duro 😅',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='srusso'),
'Cerco compagni di studio per prepararci insieme all''esame di Database. Mi blocco sempre sulle subquery correlate. Qualcuno disponibile nel pomeriggio in biblioteca?',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='aesposito'),
'Vi segnalo il canale YouTube "Fireship" per chi studia JavaScript e web dev. Spiega concetti complessi in 100 secondi in modo chiarissimo. Risorse gratuite di qualità esistono!',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='vcolombo'),
'Domanda: perché il mio div non va al centro con margin: auto? Poi ho scoperto che mancava width... 3 ore perse 😭 Il CSS è una bestia!',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='mricci'),
'Ho provato a installare Arch Linux per la prima volta. Dopo 4 ore ero ancora al bootloader. Alla fine ho reinstallato Ubuntu. Rispetto enorme per chi usa Arch nella vita reale 🎩',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='cmarino'),
'Secondo voi è più utile imparare prima Python o JavaScript? Voglio fare web dev ma non so da dove iniziare. Il percorso ITS dà basi di entrambi ma vorrei approfondirne uno.',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='lgreco'),
'Ho completato il mio primo progetto Android con Kotlin! Una semplice app per gestire una lista della spesa con Room database. Piccolo ma funzionante, sono soddisfatto 🎉',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='fbruno'),
'Reminder: fare sempre backup del codice. Il mio PC ha fatto crash stanotte e ho perso le ultime modifiche perché non avevo fatto commit. Git salva la vita, usatelo!',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='sgallo'),
'ChatGPT per il codice: utile o fa venire dipendenza? Io lo uso per capire gli errori ma provo sempre a risolvere da solo prima. Voi come lo usate per studiare?',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='aconti'),
'Ha qualcuno esperienza con Docker? Non riesco a capire la differenza pratica tra immagine e container. I tutorial spiegano la teoria ma mancano esempi concreti.',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='dmancini'),
'Partecipato al mio primo hackathon online! Non abbiamo vinto ma ho imparato più in 24 ore che in un mese di studio normale. Consigliatissimo, cercatene uno adatto al vostro livello.',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='sdeluca'),
'Studio tip: quando siete bloccati su un bug da più di 30 minuti, alzatevi, fate una passeggiata e tornate. Il 90% delle volte vedete subito il problema. Il cervello ha bisogno di staccare!',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='flombardi'),
'Qualcuno conosce risorse per prepararsi alla certificazione AWS Cloud Practitioner? Il costo dell''esame è alto ma sembra valga la pena per il mercato del lavoro.',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='mcosta'),
'Primo contributo open source fatto! Ho corretto un typo nella documentazione di un progetto GitHub, ma da qualche parte bisogna iniziare 😄 Consigli per contribuire a progetti più grandi?',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='rbarbieri'),
'SQL o NoSQL? Sto cercando di capire quando usare MongoDB invece di MySQL. Ho letto tanti articoli ma le opinioni sono sempre discordanti. Qualcuno ha esperienza pratica con entrambi?',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='efontana'),
'Ho finalmente capito la ricorsione! La chiave è stata visualizzarla come uno stack di chiamate. Se fate fatica, provate a disegnare l''albero di chiamate su carta. Aiuta tantissimo 🌳',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='amoretti'),
'Vale la pena imparare Linux per chi fa sviluppo? Ho sempre usato Windows ma tutti i tutorial assumono Mac o Linux. Sto valutando di installare WSL2.',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='lgiordano'),
'Progetto personale: sto costruendo una web app per tenere traccia delle serie TV che guardo con React e Firebase. Qualcuno vuole collaborare? Sarebbe bello fare un progetto in team!',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='tcoppola'),
'Tip per chi studia algoritmi: LeetCode è ottimo ma partite dai problemi Easy. Ho iniziato dai Medium e mi sono scoraggiato. Ora faccio 1 Easy al giorno e sto migliorando molto.',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='aferretti'),
'Qualcuno ha già fatto stage in un''azienda IT? Vorrei sapere com''è la realtà lavorativa rispetto a quello che studiamo. Si usa davvero tutto quello che ci insegnano?',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='gbenedetti'),
'"Clean Code" di Robert C. Martin è un must per chi vuole scrivere codice professionale. Lo sto leggendo adesso e mi ha già fatto riscrivere completamente un progetto!',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='cmarchetti'),
'Il debugging è un''arte. Oggi ho passato 2 ore a cercare un bug ed era un punto e virgola di troppo. Ma la soddisfazione quando trovi il problema non ha prezzo! Voi qual è il bug più stupido che avete risolto?',
@now, @now, @now),
((SELECT id FROM utenti WHERE username='spellegrini'),
'Pensiero del giorno: la programmazione non è solo scrivere codice, è risolvere problemi. Il codice è solo il mezzo. Per questo i colloqui tecnici si concentrano sul problem-solving.',
@now, @now, @now);


-- ============================================================
-- COMMENTI DEGLI STUDENTI SUI POST DEI DOCENTI
-- ============================================================

-- Commenti su post 1 avitale (SOLID)
INSERT INTO commento (id_post, id_utente, testo, created_at, data_ora, updated_at) VALUES
(@p1, (SELECT id FROM utenti WHERE username='mrossi'),    'Il SRP significa che se una classe gestisce sia il login che l''invio email va divisa in due! Ho capito bene?', @now, @now, @now),
(@p1, (SELECT id FROM utenti WHERE username='gbianchi'),  'Ho letto di SOLID su un articolo ma non ero sicura di aver capito. Con l''esempio pratico è molto più chiaro, grazie prof!', @now, @now, @now),
(@p1, (SELECT id FROM utenti WHERE username='lferrari'),  'Il principio più difficile da applicare per me è l''ISP. A volte non capisco quante interfacce creare.', @now, @now, @now),
(@p1, (SELECT id FROM utenti WHERE username='aesposito'), 'Trovato un video di Fireship che spiega SOLID in 10 minuti, lo consiglio a tutti prima della prossima lezione!', @now, @now, @now);

-- Commenti su post 2 avitale (Spring Boot CRUD)
INSERT INTO commento (id_post, id_utente, testo, created_at, data_ora, updated_at) VALUES
(@p2, (SELECT id FROM utenti WHERE username='srusso'),    'Ho iniziato! Ho già l''endpoint GET funzionante. Il POST mi dà errore 400, sto cercando di capire perché 😅', @now, @now, @now),
(@p2, (SELECT id FROM utenti WHERE username='vcolombo'),  'Posso usare PostgreSQL invece di H2? O è meglio attenersi alle specifiche per ora?', @now, @now, @now),
(@p2, (SELECT id FROM utenti WHERE username='dmancini'),  'Ho già il repo su GitHub! Qualcuno vuole fare code review reciproca? Si impara tantissimo leggendo il codice degli altri.', @now, @now, @now);

-- Commenti su post 1 mferrara (Flexbox)
INSERT INTO commento (id_post, id_utente, testo, created_at, data_ora, updated_at) VALUES
(@p3, (SELECT id FROM utenti WHERE username='cmarino'),   'Finalmente ho capito align-items vs justify-content! Ogni volta che li confondevo dovevo tornare a googlare.', @now, @now, @now),
(@p3, (SELECT id FROM utenti WHERE username='efontana'),  'Ho fatto una navbar ma non riesco a centrare verticalmente il testo nei link. Uso align-items: center sul flex container?', @now, @now, @now),
(@p3, (SELECT id FROM utenti WHERE username='mcosta'),    'Io uso Flexbox per quasi tutto ormai. La combo display:flex + gap è devastante per la produttività!', @now, @now, @now);

-- Commenti su post 1 griva (var/let/const)
INSERT INTO commento (id_post, id_utente, testo, created_at, data_ora, updated_at) VALUES
(@p5, (SELECT id FROM utenti WHERE username='lgreco'),    'Quindi usando var dentro un if block la variabile è accessibile anche fuori? Questo mi ha causato bug assurdi!', @now, @now, @now),
(@p5, (SELECT id FROM utenti WHERE username='fbruno'),    'Ho letto che il team TypeScript raccomanda sempre const. Se hai bisogno di let spesso forse rivedere la struttura del codice.', @now, @now, @now),
(@p5, (SELECT id FROM utenti WHERE username='sgallo'),    'Prof. quand''è che usiamo ancora var? Solo per compatibilità con browser molto vecchi?', @now, @now, @now),
(@p5, (SELECT id FROM utenti WHERE username='rbarbieri'), 'L''hoisting di var mi ha fatto impazzire quando ho iniziato. Ora capisco perché ESLint per default vieta var!', @now, @now, @now);

-- Commenti su post 1 pserra (Python type hints)
INSERT INTO commento (id_post, id_utente, testo, created_at, data_ora, updated_at) VALUES
(@p7, (SELECT id FROM utenti WHERE username='aconti'),    'Ho iniziato ad usare i type hints dopo che mypy mi ha trovato un bug che ignoravo da settimane. Convertito!', @now, @now, @now),
(@p7, (SELECT id FROM utenti WHERE username='amoretti'),  'La sintassi str | int di Python 3.10 è molto più pulita. Prima Union[str, int] sembrava Java 😅', @now, @now, @now),
(@p7, (SELECT id FROM utenti WHERE username='tcoppola'),  'I type hints migliorano anche l''autocompletamento su VS Code! Finalmente i suggerimenti sono precisi.', @now, @now, @now);

-- Commenti su post 1 rgentile (3NF)
INSERT INTO commento (id_post, id_utente, testo, created_at, data_ora, updated_at) VALUES
(@p9, (SELECT id FROM utenti WHERE username='srusso'),    'Se ho una tabella Ordini con cliente_nome e cliente_email, devo creare una tabella Clienti separata? È violazione 3NF?', @now, @now, @now),
(@p9, (SELECT id FROM utenti WHERE username='flombardi'),  'La normalizzazione ha senso in teoria ma in pratica si denormalizza per performance. Quando è giusto farlo prof?', @now, @now, @now),
(@p9, (SELECT id FROM utenti WHERE username='aferretti'),  'Ho rifatto il mio database applicando la 3NF e ora le query sono molto più semplici. Vale la pena!', @now, @now, @now);

-- Commenti su post 1 cfabbri (OSI model)
INSERT INTO commento (id_post, id_utente, testo, created_at, data_ora, updated_at) VALUES
(@p11, (SELECT id FROM utenti WHERE username='ecaruso'),   'Il router lavora al livello 3 e instrada pacchetti IP, lo switch lavora al livello 2 e inoltra frame MAC. Giusto prof?', @now, @now, @now),
(@p11, (SELECT id FROM utenti WHERE username='nferri'),    'Avevo sempre confuso i due! Grazie per la domanda, mi ha spinto a ripassare il livello 2 e 3 insieme.', @now, @now, @now),
(@p11, (SELECT id FROM utenti WHERE username='rsantoro'),  'Posso partecipare in remoto al laboratorio con Packet Tracer? Ho installato tutto a casa.', @now, @now, @now);

-- Commenti su post 1 ebassi (Jetpack Compose)
INSERT INTO commento (id_post, id_utente, testo, created_at, data_ora, updated_at) VALUES
(@p13, (SELECT id FROM utenti WHERE username='lgreco'),    'Sto usando Compose per il mio progetto personale! La curva di apprendimento è ripida ma una volta capita è molto più veloce dell''XML.', @now, @now, @now),
(@p13, (SELECT id FROM utenti WHERE username='dpalumbo'),  'Come gestite il ViewModel con Compose? Uso collectAsState() sugli StateFlow ma non so se è l''approccio migliore.', @now, @now, @now),
(@p13, (SELECT id FROM utenti WHERE username='samato'),    'Ho provato Compose in un progetto di test. L''hot reload funziona benissimo, che comodità rispetto a ricompilare tutto!', @now, @now, @now);

-- Commenti su post 1 icaputo (SQL Injection)
INSERT INTO commento (id_post, id_utente, testo, created_at, data_ora, updated_at) VALUES
(@p15, (SELECT id FROM utenti WHERE username='gbenedetti'), 'Ho testato la SQL injection su HackTheBox e mi ha aperto un mondo. La cybersecurity è affascinante!', @now, @now, @now),
(@p15, (SELECT id FROM utenti WHERE username='cmarchetti'),  'Con Spring JPA usiamo già prepared statements automaticamente, giusto? O bisogna fare qualcosa di specifico?', @now, @now, @now),
(@p15, (SELECT id FROM utenti WHERE username='spellegrini'), 'Impressionante come una semplice apostrofe possa compromettere un intero sistema. Argomento fondamentale!', @now, @now, @now);

-- Commenti su post 1 msilvestri (AWS vs Azure)
INSERT INTO commento (id_post, id_utente, testo, created_at, data_ora, updated_at) VALUES
(@p17, (SELECT id FROM utenti WHERE username='lgiordano'),   'Ho usato il free tier di AWS per un progetto personale. L''interfaccia è complessa ma le risorse gratuite bastano per imparare.', @now, @now, @now),
(@p17, (SELECT id FROM utenti WHERE username='mricci'),      'Firebase di Google è più facile per iniziare. Meno configurazione per un primo progetto.', @now, @now, @now),
(@p17, (SELECT id FROM utenti WHERE username='ecaruso'),     'Prof. quali certificazioni cloud hanno più valore nel mercato del lavoro italiano? AWS o Azure?', @now, @now, @now);

-- Commenti su post 1 dmonti (TypeScript)
INSERT INTO commento (id_post, id_utente, testo, created_at, data_ora, updated_at) VALUES
(@p19, (SELECT id FROM utenti WHERE username='nferri'),      'Ho iniziato TypeScript per il progetto Angular e non torno a JS puro. I messaggi di errore ti dicono ESATTAMENTE cosa non va!', @now, @now, @now),
(@p19, (SELECT id FROM utenti WHERE username='rsantoro'),    'Resistenze zero! Ho visto un collega perdere 4 ore per un bug che TypeScript avrebbe rilevato in 0 secondi.', @now, @now, @now),
(@p19, (SELECT id FROM utenti WHERE username='dpalumbo'),    'La differenza tra interface e type alias quando si usa una rispetto all''altra? Li vedo usati indifferentemente.', @now, @now, @now),
(@p19, (SELECT id FROM utenti WHERE username='samato'),      'TypeScript con Angular è una coppia perfetta! Funziona ancora meglio con il language service di VS Code.', @now, @now, @now);


-- ============================================================
-- LIKES SUI POST DEI DOCENTI
-- ============================================================
INSERT INTO likes (id_post, id_utente, created_at, data_ora, updated_at) VALUES
(@p1,  (SELECT id FROM utenti WHERE username='mrossi'),     @now, @now, @now),
(@p1,  (SELECT id FROM utenti WHERE username='gbianchi'),   @now, @now, @now),
(@p1,  (SELECT id FROM utenti WHERE username='lferrari'),   @now, @now, @now),
(@p1,  (SELECT id FROM utenti WHERE username='srusso'),     @now, @now, @now),
(@p1,  (SELECT id FROM utenti WHERE username='dmancini'),   @now, @now, @now),
(@p2,  (SELECT id FROM utenti WHERE username='vcolombo'),   @now, @now, @now),
(@p2,  (SELECT id FROM utenti WHERE username='mricci'),     @now, @now, @now),
(@p2,  (SELECT id FROM utenti WHERE username='cmarino'),    @now, @now, @now),
(@p2,  (SELECT id FROM utenti WHERE username='efontana'),   @now, @now, @now),
(@p3,  (SELECT id FROM utenti WHERE username='lgreco'),     @now, @now, @now),
(@p3,  (SELECT id FROM utenti WHERE username='fbruno'),     @now, @now, @now),
(@p3,  (SELECT id FROM utenti WHERE username='mcosta'),     @now, @now, @now),
(@p4,  (SELECT id FROM utenti WHERE username='sgallo'),     @now, @now, @now),
(@p4,  (SELECT id FROM utenti WHERE username='rbarbieri'),  @now, @now, @now),
(@p5,  (SELECT id FROM utenti WHERE username='aconti'),     @now, @now, @now),
(@p5,  (SELECT id FROM utenti WHERE username='amoretti'),   @now, @now, @now),
(@p5,  (SELECT id FROM utenti WHERE username='tcoppola'),   @now, @now, @now),
(@p6,  (SELECT id FROM utenti WHERE username='aferretti'),  @now, @now, @now),
(@p6,  (SELECT id FROM utenti WHERE username='gbenedetti'), @now, @now, @now),
(@p7,  (SELECT id FROM utenti WHERE username='cmarchetti'),  @now, @now, @now),
(@p7,  (SELECT id FROM utenti WHERE username='spellegrini'), @now, @now, @now),
(@p8,  (SELECT id FROM utenti WHERE username='ecaruso'),    @now, @now, @now),
(@p8,  (SELECT id FROM utenti WHERE username='nferri'),     @now, @now, @now),
(@p9,  (SELECT id FROM utenti WHERE username='rsantoro'),   @now, @now, @now),
(@p9,  (SELECT id FROM utenti WHERE username='dpalumbo'),   @now, @now, @now),
(@p9,  (SELECT id FROM utenti WHERE username='samato'),     @now, @now, @now),
(@p10, (SELECT id FROM utenti WHERE username='mrossi'),     @now, @now, @now),
(@p10, (SELECT id FROM utenti WHERE username='srusso'),     @now, @now, @now),
(@p11, (SELECT id FROM utenti WHERE username='lferrari'),   @now, @now, @now),
(@p11, (SELECT id FROM utenti WHERE username='vcolombo'),   @now, @now, @now),
(@p11, (SELECT id FROM utenti WHERE username='ecaruso'),    @now, @now, @now),
(@p12, (SELECT id FROM utenti WHERE username='nferri'),     @now, @now, @now),
(@p12, (SELECT id FROM utenti WHERE username='rsantoro'),   @now, @now, @now),
(@p13, (SELECT id FROM utenti WHERE username='lgreco'),     @now, @now, @now),
(@p13, (SELECT id FROM utenti WHERE username='dpalumbo'),   @now, @now, @now),
(@p13, (SELECT id FROM utenti WHERE username='samato'),     @now, @now, @now),
(@p14, (SELECT id FROM utenti WHERE username='mricci'),     @now, @now, @now),
(@p14, (SELECT id FROM utenti WHERE username='cmarino'),    @now, @now, @now),
(@p15, (SELECT id FROM utenti WHERE username='gbenedetti'), @now, @now, @now),
(@p15, (SELECT id FROM utenti WHERE username='cmarchetti'),  @now, @now, @now),
(@p15, (SELECT id FROM utenti WHERE username='spellegrini'), @now, @now, @now),
(@p15, (SELECT id FROM utenti WHERE username='flombardi'),   @now, @now, @now),
(@p16, (SELECT id FROM utenti WHERE username='aferretti'),  @now, @now, @now),
(@p16, (SELECT id FROM utenti WHERE username='tcoppola'),   @now, @now, @now),
(@p17, (SELECT id FROM utenti WHERE username='lgiordano'),  @now, @now, @now),
(@p17, (SELECT id FROM utenti WHERE username='mricci'),     @now, @now, @now),
(@p17, (SELECT id FROM utenti WHERE username='flombardi'),  @now, @now, @now),
(@p18, (SELECT id FROM utenti WHERE username='aconti'),     @now, @now, @now),
(@p18, (SELECT id FROM utenti WHERE username='dmancini'),   @now, @now, @now),
(@p19, (SELECT id FROM utenti WHERE username='nferri'),     @now, @now, @now),
(@p19, (SELECT id FROM utenti WHERE username='rsantoro'),   @now, @now, @now),
(@p19, (SELECT id FROM utenti WHERE username='dpalumbo'),   @now, @now, @now),
(@p19, (SELECT id FROM utenti WHERE username='samato'),     @now, @now, @now),
(@p20, (SELECT id FROM utenti WHERE username='gbianchi'),   @now, @now, @now),
(@p20, (SELECT id FROM utenti WHERE username='efontana'),   @now, @now, @now);
