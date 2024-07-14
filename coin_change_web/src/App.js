import React, {useState, useEffect, useRef} from "react";
import axios from "axios";
import "./coinChange.css"

function App() {
    const [coinDenominator, setCoinDenominator] = useState([]);
    const [selectedCoins, setSelectedCoins] = useState([]);
    const [result, setResult] = useState("");

    const targetMoneyRef = useRef(null);

    useEffect(() => {
        console.log("Retrieving coinDenominator list from server");
        retrieveCoinDenominator();
    }, []);

    //retrieve coin denominations from server
    function retrieveCoinDenominator() {
        axios
            .get("http://localhost:8080/coinChange")
            .then(response => {
                setCoinDenominator(response.data);
                console.log(response.data);
        }).catch(e => {
            console.log(e);
        });
    }

    //submit the form to server
    function handleSubmit(event) {
        event.preventDefault();
        const data = {
            targetMoney: parseFloat(targetMoneyRef.current.value),
            selectedCoins: selectedCoins
        }
        axios.post("http://localhost:8080/coinChange/calculate",data)
            .then(response => {
                setResult(response.data);
            })
            .catch(e => {
                console.log(e);
            })
    }

    //change SelectedCoins when checkbox is changed
    function handleCheckboxChange(denomination) {
        setSelectedCoins((prev) => {
            // Check if the clicked denomination is already in the selectedDenominations array.
            if (prev.includes(denomination)) {
                // If it is, filter it out (remove it from the array).
                return prev.filter((d) => d !== denomination);
            } else {
                // If it isn't, add it to the array.
                return [...prev, denomination];
            }
        });
    }

    function generateDenominations() {
        return (
            <div>
                <label>Coin Denominations:</label>
                <table>
                    <tbody>
                    <tr>
                        {coinDenominator.map((denomination) => (
                            <td key={denomination}>
                                <label>
                                    <input
                                        type="checkbox"
                                        value={denomination}
                                        checked={selectedCoins.includes(denomination)}
                                        onChange={() => handleCheckboxChange(denomination)}
                                    />
                                    {denomination}
                                </label>
                            </td>
                        ))}
                    </tr>
                    </tbody>
                </table>
            </div>
        );
    }

    return (
        <div>
            <h1>Coin change</h1>
            <hr width="100%"/>
            <form onSubmit={handleSubmit}>
                <label>
                    Target Money:
                    <input type="number" step="0.01" name="targetMoney" ref={targetMoneyRef}/>
                </label>
                {generateDenominations()}
                <button type="submit">Calculate</button>
            </form>
            <label>
                Result:
                <textarea value={result} readOnly rows="5"/>
            </label>
        </div>
    );
}

export default App;
