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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: courses; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.courses (
    course_id bigint NOT NULL,
    course_name character varying(255)
);


ALTER TABLE public.courses OWNER TO test;

--
-- Name: courses_course_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

ALTER TABLE public.courses ALTER COLUMN course_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.courses_course_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: grades; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.grades (
    grade_id bigint NOT NULL,
    grade smallint,
    lesson_id bigint,
    student_id bigint
);


ALTER TABLE public.grades OWNER TO test;

--
-- Name: grades_grade_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

ALTER TABLE public.grades ALTER COLUMN grade_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.grades_grade_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: groups; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.groups (
    group_id bigint NOT NULL,
    group_name character varying(255) NOT NULL,
    course_id bigint
);


ALTER TABLE public.groups OWNER TO test;

--
-- Name: groups_group_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

ALTER TABLE public.groups ALTER COLUMN group_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.groups_group_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: lessons; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.lessons (
    lesson_id bigint NOT NULL,
    "time" timestamp without time zone,
    lessons_group bigint,
    teacher_id bigint,
    theme_id bigint
);


ALTER TABLE public.lessons OWNER TO test;

--
-- Name: lessons_lesson_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

ALTER TABLE public.lessons ALTER COLUMN lesson_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.lessons_lesson_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: students; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.students (
    student_id bigint NOT NULL,
    name character varying(255) NOT NULL,
    surname character varying(255) NOT NULL,
    group_id bigint
);


ALTER TABLE public.students OWNER TO test;

--
-- Name: students_student_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

ALTER TABLE public.students ALTER COLUMN student_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.students_student_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: teachers; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.teachers (
    teacher_id bigint NOT NULL,
    name character varying(255),
    surname character varying(255)
);


ALTER TABLE public.teachers OWNER TO test;

--
-- Name: teachers_teacher_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

ALTER TABLE public.teachers ALTER COLUMN teacher_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.teachers_teacher_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: themes; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.themes (
    theme_id bigint NOT NULL,
    name character varying(255)
);


ALTER TABLE public.themes OWNER TO test;

--
-- Name: themes_theme_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

ALTER TABLE public.themes ALTER COLUMN theme_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.themes_theme_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: courses courses_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.courses
    ADD CONSTRAINT courses_pkey PRIMARY KEY (course_id);


--
-- Name: grades grades_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.grades
    ADD CONSTRAINT grades_pkey PRIMARY KEY (grade_id);


--
-- Name: groups groups_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT groups_pkey PRIMARY KEY (group_id);


--
-- Name: lessons lessons_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.lessons
    ADD CONSTRAINT lessons_pkey PRIMARY KEY (lesson_id);


--
-- Name: students students_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT students_pkey PRIMARY KEY (student_id);


--
-- Name: teachers teachers_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.teachers
    ADD CONSTRAINT teachers_pkey PRIMARY KEY (teacher_id);


--
-- Name: themes themes_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.themes
    ADD CONSTRAINT themes_pkey PRIMARY KEY (theme_id);


--
-- Name: grades fk13a16545m7vvrcspc999r15s9; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.grades
    ADD CONSTRAINT fk13a16545m7vvrcspc999r15s9 FOREIGN KEY (student_id) REFERENCES public.students(student_id);


--
-- Name: lessons fk1p9er171vafjiqhgmhrjjh1b9; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.lessons
    ADD CONSTRAINT fk1p9er171vafjiqhgmhrjjh1b9 FOREIGN KEY (theme_id) REFERENCES public.themes(theme_id);


--
-- Name: lessons fkbr76cuebuufbbltujpfq04tto; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.lessons
    ADD CONSTRAINT fkbr76cuebuufbbltujpfq04tto FOREIGN KEY (teacher_id) REFERENCES public.teachers(teacher_id);


--
-- Name: groups fkf19vc2y7m7nnmbr8usoitb3gp; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.groups
    ADD CONSTRAINT fkf19vc2y7m7nnmbr8usoitb3gp FOREIGN KEY (course_id) REFERENCES public.courses(course_id);


--
-- Name: students fkmsev1nou0j86spuk5jrv19mss; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.students
    ADD CONSTRAINT fkmsev1nou0j86spuk5jrv19mss FOREIGN KEY (group_id) REFERENCES public.groups(group_id);


--
-- Name: grades fkmt7ytccr6ebljxbu05mlb5fif; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.grades
    ADD CONSTRAINT fkmt7ytccr6ebljxbu05mlb5fif FOREIGN KEY (lesson_id) REFERENCES public.lessons(lesson_id);


--
-- Name: lessons fksr5knbqygs7ro21dy2sot3ffc; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.lessons
    ADD CONSTRAINT fksr5knbqygs7ro21dy2sot3ffc FOREIGN KEY (lessons_group) REFERENCES public.groups(group_id);


--
-- PostgreSQL database dump complete
--

