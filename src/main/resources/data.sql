DELETE FROM message;
INSERT INTO message (id, content, author, origin, channel, photo) VALUES
(1, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Albert Einstein', '2020-03-10 10:30:40', 'webframeworks', ''),
(2, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Albert Einstein', '2020-03-10 10:31:22', 'technotrends', ''),
(3, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Mac Afee', '2020-03-10 10:38:11', 'fun', ''),
(4, 'Cras sit amet nibh libero, in gravida nulla. Nulla vel metus scelerisque ante sollicitudin commodo. Cras purus odio, vestibulum in vulputate at, tempus viverra turpis.', 'Tony Stark', '2020-03-10 10:42:57', 'secret', '');

/* encrypted password for id 1..4 is 1234* */
DELETE FROM member;
INSERT INTO member (id, prename, lastname, password, username, authority) VALUES
(1, 'Albert', 'Einstein', 'e4394ac05cbb8efaf523524291503cc8349f4c76a1bad2e8aafe7fd30ce0c28cdab67e7103ce317e', 'albert.einstein', 'admin'),
(2, 'Mac',  'Afee', 'e4394ac05cbb8efaf523524291503cc8349f4c76a1bad2e8aafe7fd30ce0c28cdab67e7103ce317e', 'mac.afee', 'member'),
(3, 'Tony',  'Stark', 'e4394ac05cbb8efaf523524291503cc8349f4c76a1bad2e8aafe7fd30ce0c28cdab67e7103ce317e', 'toni.stark', 'supervisor'),
(4, 'Wilhelm',  'Tell', 'e4394ac05cbb8efaf523524291503cc8349f4c76a1bad2e8aafe7fd30ce0c28cdab67e7103ce317e', 'wilhelm.tell', 'member');
