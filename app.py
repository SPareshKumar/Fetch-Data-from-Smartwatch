from flask import Flask, request, jsonify

app = Flask(__name__)

# Define a route for receiving heart rate data
@app.route('/receive-heart-rate', methods=['POST'])
def receive_heart_rate():
    # Check if the request contains JSON data
    if request.is_json:
        # Parse the JSON data
        data = request.get_json()
        
        # Extract heart rate from the received data
        heart_rate = data.get('heartRate')  # Adjust field name to match MainActivity

        # Log or print the received heart rate for debugging
        print(f"Received heart rate: {heart_rate}")

        # Optionally, you could store the data in a database or perform other processing here

        # Return a success response
        return jsonify({"message": "Heart rate data received successfully!"}), 200
    else:
        # If the request doesn't contain JSON data, return an error
        return jsonify({"error": "Request must contain JSON data."}), 400

# Run the server
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)


