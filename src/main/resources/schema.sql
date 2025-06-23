-- Create users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL UNIQUE,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'THEATER_OWNER', 'STAFF', 'CUSTOMER')),
    is_active BOOLEAN NOT NULL DEFAULT true,
    username VARCHAR(255) NOT NULL UNIQUE,
    full_name VARCHAR(255),
    date_of_birth DATE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create theaters table
CREATE TABLE theaters (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    opening_time TIME,
    closing_time TIME,
    total_screens INTEGER,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    theater_owner_id BIGINT
);

-- Create screens table
CREATE TABLE screens (
    id BIGSERIAL PRIMARY KEY,
    screen_name VARCHAR(255) NOT NULL,
    screen_type VARCHAR(255) NOT NULL,
    total_seats INTEGER NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    theater_id BIGINT NOT NULL REFERENCES theaters(id),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create seat_types table
CREATE TABLE seat_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    price_multiplier DECIMAL(10,2) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true
);

-- Create seats table
CREATE TABLE seats (
    id BIGSERIAL PRIMARY KEY,
    screen_id BIGINT NOT NULL REFERENCES screens(id),
    seat_number VARCHAR(10) NOT NULL,
    seat_type_id BIGINT NOT NULL REFERENCES seat_types(id),
    is_active BOOLEAN NOT NULL DEFAULT true,
    UNIQUE (screen_id, seat_number)
);

-- Create movies table
CREATE TABLE movies (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    title_vi VARCHAR(255),
    description TEXT,
    duration INTEGER,
    language VARCHAR(50),
    genre VARCHAR(100),
    release_date DATE,
    poster_url TEXT,
    backdrop_url TEXT,
    trailer_url TEXT,
    director VARCHAR(255),
    actor VARCHAR(255),
    rating FLOAT,
    country VARCHAR(100),
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create showtimes table
CREATE TABLE showtimes (
    id BIGSERIAL PRIMARY KEY,
    movie_id BIGINT NOT NULL REFERENCES movies(id),
    theater_id BIGINT NOT NULL REFERENCES theaters(id),
    screen_id BIGINT NOT NULL REFERENCES screens(id),
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true
);

-- Create bookings table
CREATE TABLE bookings (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    showtime_id BIGINT NOT NULL REFERENCES showtimes(id),
    booking_time TIMESTAMP NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED')),
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create booking_seats table
CREATE TABLE booking_seats (
    booking_id BIGINT NOT NULL REFERENCES bookings(id),
    seat_id BIGINT NOT NULL REFERENCES seats(id),
    price DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (booking_id, seat_id)
);

-- Create booking_foods table
CREATE TABLE booking_foods (
    booking_id BIGINT NOT NULL REFERENCES bookings(id),
    food_inventory_id BIGINT NOT NULL REFERENCES theater_food_inventory(id),
    quantity INTEGER NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (booking_id, food_inventory_id)
);

-- Create payments table
CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    booking_id BIGINT NOT NULL REFERENCES bookings(id) UNIQUE,
    amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(20) NOT NULL CHECK (payment_method IN ('CREDIT_CARD', 'DEBIT_CARD', 'CASH', 'DIGITAL_WALLET', 'BANK_TRANSFER')),
    status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED', 'REFUNDED')),
    transaction_id VARCHAR(255),
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create theater_food_inventory table
CREATE TABLE theater_food_inventory (
    id BIGSERIAL PRIMARY KEY,
    theater_id BIGINT NOT NULL REFERENCES theaters(id),
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    image_url TEXT NOT NULL,
    category VARCHAR(255) NOT NULL,
    preparation_time INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    UNIQUE (theater_id, name)
);

-- Create vouchers table
CREATE TABLE vouchers (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(255) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    discount_amount DECIMAL(10,2) NOT NULL,
    min_price DECIMAL(10,2),
    type VARCHAR(20) CHECK (type IN ('new_user', 'seasonal')),
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    max_uses INTEGER NOT NULL,
    used_count INTEGER NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create theater_owner table
CREATE TABLE theater_owner (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    position VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create user_vouchers table
CREATE TABLE user_vouchers (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    voucher_id BIGINT NOT NULL REFERENCES vouchers(id),
    is_used BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    UNIQUE (user_id, voucher_id)
);

-- Add indexes for better query performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_movies_release_date ON movies(release_date);
CREATE INDEX idx_showtimes_start_time ON showtimes(start_time);
CREATE INDEX idx_showtimes_movie_id ON showtimes(movie_id);
CREATE INDEX idx_showtimes_screen_id ON showtimes(screen_id);
CREATE INDEX idx_bookings_user_id ON bookings(user_id);
CREATE INDEX idx_bookings_showtime_id ON bookings(showtime_id);
CREATE INDEX idx_booking_seats_booking_id ON booking_seats(booking_id);
CREATE INDEX idx_booking_seats_seat_id ON booking_seats(seat_id);
CREATE INDEX idx_seats_screen_id ON seats(screen_id);
CREATE INDEX idx_screens_theater_id ON screens(theater_id);
CREATE INDEX idx_theaters_theater_owner_id ON theaters(theater_owner_id);

-- === MIGRATION: Staff -> TheaterOwner ===
-- Đổi tên bảng staff thành theater_owner
ALTER TABLE staff RENAME TO theater_owner;

-- Xóa cột theater_id khỏi bảng theater_owner
ALTER TABLE theater_owner DROP COLUMN theater_id;

-- Thêm cột theater_owner_id vào bảng theaters
ALTER TABLE theaters ADD CONSTRAINT fk_theater_owner FOREIGN KEY (theater_owner_id) REFERENCES theater_owner(id);

-- Xóa cột position và salary khỏi bảng theater_owner
ALTER TABLE theater_owner DROP COLUMN IF EXISTS position;
ALTER TABLE theater_owner DROP COLUMN IF EXISTS salary; 

ALTER TABLE vouchers
ADD COLUMN min_price DECIMAL(10,2),
ADD COLUMN type VARCHAR(20) CHECK (type IN ('new_user', 'seasonal'));