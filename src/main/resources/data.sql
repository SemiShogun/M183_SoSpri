DELETE FROM message;
INSERT INTO message (id, content, author, origin, channel, photo) VALUES
(1, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Albert Einstein', '2020-03-10 10:30:40', 'webframeworks', ''),
(2, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Albert Einstein', '2020-03-10 10:31:22', 'technotrends', ''),
(3, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Mac Afee', '2020-03-10 10:38:11', 'fun', ''),
(4, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Tony Stark', '2020-03-10 10:42:57', 'secret', '');

/* encrypted password for id 1..4 is 1234* */
DELETE FROM member;
INSERT INTO member (id, prename, lastname, password, username, authority, email, tfa, secret) VALUES
(1, 'Albert', 'Einstein', '9f58d4ed84d1a51da3f67a59af01c3af15f0ef88430a39d52bc913110989878283263fa8d8e0b229', 'albert.einstein', 'admin', '', False, ''),
(2, 'Mac',  'Afee', '9f58d4ed84d1a51da3f67a59af01c3af15f0ef88430a39d52bc913110989878283263fa8d8e0b229', 'mac.afee', 'member', '', False, ''),
(3, 'Tony',  'Stark', '9f58d4ed84d1a51da3f67a59af01c3af15f0ef88430a39d52bc913110989878283263fa8d8e0b229', 'toni.stark', 'supervisor', '', False, ''),
(4, 'Wilhelm',  'Tell', '9f58d4ed84d1a51da3f67a59af01c3af15f0ef88430a39d52bc913110989878283263fa8d8e0b229', 'wilhelm.tell', 'member', '', False, '');
