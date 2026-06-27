import { useEffect, useState } from "react";
import axios from "axios";

function AIAnalysisModal({
    incidentId,
    onClose
}) {

    const [analysis, setAnalysis] =
        useState(null);

    const [loading, setLoading] =
        useState(true);

    useEffect(() => {

        fetchAnalysis();

    }, []);

    const fetchAnalysis = async () => {

        try {

            const response =
                await axios.get(
                    `http://localhost:8080/ai/incident/${incidentId}`
                );

            setAnalysis(
                response.data
            );

        }
        catch(error) {

            console.error(error);

        }
        finally {

            setLoading(false);

        }

    };

    return (

        <div
            style={{
                position: "fixed",
                top: 0,
                left: 0,
                width: "100%",
                height: "100%",
                background: "rgba(0,0,0,0.5)",
                display: "flex",
                justifyContent: "center",
                alignItems: "center"
            }}
        >

            <div
                style={{
                    width: "600px",
                    background: "white",
                    padding: "25px",
                    borderRadius: "10px"
                }}
            >

                <h2>
                    AI Incident Analysis
                </h2>

                {
                    loading ?

                    <p>
                        Loading...
                    </p>

                    :

                    analysis &&

                    <>

                        <h3>
                            Disaster Type
                        </h3>

                        <p>
                            {analysis.disasterType}
                        </p>

                        <h3>
                            Confidence
                        </h3>

                        <p>
                            {analysis.confidence} %
                        </p>

                        <h3>
                            Detected Objects
                        </h3>

                        <p>
                            {analysis.detectedObjects}
                        </p>

                        <h3>
                            AI Assessment
                        </h3>

                        <p>
                            {analysis.summary}
                        </p>

                    </>

                }

                <button
                    onClick={onClose}
                    style={{
                        marginTop: "20px"
                    }}
                >
                    Close
                </button>

            </div>

        </div>

    );

}

export default AIAnalysisModal;