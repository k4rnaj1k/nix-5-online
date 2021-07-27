--
-- PostgreSQL database dump
--

-- Dumped from database version 13.3
-- Dumped by pg_dump version 13.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: courses; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.courses (course_id, course_name) FROM stdin;
1	java
\.


--
-- Data for Name: groups; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.groups (group_id, group_name, course_id) FROM stdin;
1	nix 5 online	1
2	nix 6 online	1
\.


--
-- Data for Name: teachers; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.teachers (teacher_id, name, surname) FROM stdin;
1	Some	Teacher
\.


--
-- Data for Name: themes; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.themes (theme_id, name) FROM stdin;
1	jpa/jdbc
2	reflection
\.


--
-- Data for Name: lessons; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.lessons (lesson_id, "time", lessons_group, teacher_id, theme_id) FROM stdin;
1	2021-07-27 16:34:54.923	1	1	1
2	2021-07-27 16:34:54.93	2	1	2
\.


--
-- Data for Name: students; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.students (student_id, name, surname, group_id) FROM stdin;
1	Some	Student	2
2	Vlad	Liasota	1
\.


--
-- Data for Name: grades; Type: TABLE DATA; Schema: public; Owner: test
--

COPY public.grades (grade_id, grade, lesson_id, student_id) FROM stdin;
1	10	1	2
\.


--
-- Name: courses_course_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.courses_course_id_seq', 1, true);


--
-- Name: grades_grade_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.grades_grade_id_seq', 1, true);


--
-- Name: groups_group_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.groups_group_id_seq', 2, true);


--
-- Name: lessons_lesson_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.lessons_lesson_id_seq', 2, true);


--
-- Name: students_student_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.students_student_id_seq', 2, true);


--
-- Name: teachers_teacher_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.teachers_teacher_id_seq', 1, true);


--
-- Name: themes_theme_id_seq; Type: SEQUENCE SET; Schema: public; Owner: test
--

SELECT pg_catalog.setval('public.themes_theme_id_seq', 2, true);


--
-- PostgreSQL database dump complete
--

