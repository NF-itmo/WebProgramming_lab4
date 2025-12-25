-- Auth Service
CREATE SCHEMA IF NOT EXISTS auth_service;

CREATE TABLE IF NOT EXISTS auth_service."Users" (
  id SERIAL PRIMARY KEY,
  username VARCHAR(60) UNIQUE NOT NULL,
  password_hash VARCHAR(60) NOT NULL
);


-- History Service
CREATE SCHEMA IF NOT EXISTS history_service;

CREATE TABLE IF NOT EXISTS history_service."ResultGroups" (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL,
  name VARCHAR(255) NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE IF NOT EXISTS history_service."PointResults" (
  id SERIAL PRIMARY KEY,
  group_id INTEGER NOT NULL REFERENCES history_service."ResultGroups"(id) ON DELETE CASCADE,
  x REAL NOT NULL,
  y REAL NOT NULL,
  r REAL NOT NULL,
  is_hitted BOOLEAN NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);