import "./Main.css";

import Form from "../../modules/plotForm/PlotForm";
import Plot from "../../modules/plot/Plot";
import { ColumnLayout as ContentContainer } from "@packages/shared";
import Header from "../../components/header/Header";
import History from "../../modules/history/History";
import Groups from "../../modules/groups/Groups";

const Main = () => {
    return (
        <ContentContainer>
            <Header/>
            <Groups/>
            <Plot/>
            <Form/>
            <History/>
        </ContentContainer>
    );
}

export default Main;