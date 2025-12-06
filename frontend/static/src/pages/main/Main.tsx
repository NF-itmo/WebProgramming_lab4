import "./Main.css";

import Form from "../../modules/plotForm/PlotForm";
import Plot from "../../modules/plot/Plot";
import ContentContainer from "../../UI/layouts/columnLayout/ColumnLayout";
import Header from "../../components/header/Header";
import History from "../../modules/history/History";
import { useAppSelector } from "../../store/hooks/redux";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Main = () => {
    const { token } = useAppSelector((state) => state.token);
    const navigate = useNavigate();

    useEffect(() => {
            if (token === "") {
                navigate("/login")
            }
        },
        [token]
    )
    return (
        <ContentContainer>
            <Header/>
            <Plot/>
            <Form/>
            <History/>
        </ContentContainer>
    );
}

export default Main;