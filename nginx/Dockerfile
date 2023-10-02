# Use an official NGINX image as the base
FROM nginx

# Remove the default NGINX configuration file
RUN rm /etc/nginx/conf.d/default.conf

# Copy your custom NGINX configuration file
COPY nginx.conf /etc/nginx/conf.d/

COPY ./certs /etc/nginx/certs

# Expose port 80 (or any other ports you want to use)

# Start NGINX in the foreground
CMD ["nginx", "-g", "daemon off;"]